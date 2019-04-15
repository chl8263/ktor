package io.ktor.util.collections

import io.ktor.util.*
import kotlinx.atomicfu.*
import kotlinx.atomicfu.AtomicInt
import kotlin.native.concurrent.*

@InternalAPI
actual class ConcurrentMap<Key, Value> : MutableMap<Key, Value> {
    private val EmptyNode = DataNode<Key, Value>(null, null)

    private val INITIAL_CAPACITY = 64
    private val RESIZE_FACTOR = 0.5

    private val data: AtomicReference<AtomicArray<DataNode<Key, Value>?>?> = TODO()

    private val actualSize: AtomicInt = atomic(0)
    private val capacity: AtomicInt = atomic(0)

    private val resizeLock = ReadWriteLock()

    init {
        freeze()
    }

    override val size: Int
        get() = actualSize.value

    override fun containsKey(key: Key): Boolean = resizeLock.read {
        return unsafeGet(key) != null
    }

    override fun containsValue(value: Value): Boolean = resizeLock.read {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(key: Key): Value? = resizeLock.read {
        return unsafeGet(key)
    }

    override fun isEmpty(): Boolean = resizeLock.read {
        return actualSize.value == 0
    }

    override val entries: MutableSet<MutableMap.MutableEntry<Key, Value>>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val keys: MutableSet<Key>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val values: MutableCollection<Value>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun clear() {
        data.value = null
    }

    override fun put(key: Key, value: Value): Value? = resizeLock.read {
        unsafePut(DataNode(key, value))
    }

    override fun putAll(from: Map<out Key, Value>): Unit = resizeLock.read {
        for ((key, value) in from.entries) {
            unsafePut(DataNode(key, value))
        }
    }

    override fun remove(key: Key): Value? = resizeLock.read {
        TODO()
    }

    override fun equals(other: Any?): Boolean = resizeLock.read {
        TODO()
        return super.equals(other)
    }

    override fun hashCode(): Int = resizeLock.read {
        TODO()
        return super.hashCode()
    }

    private fun resize(newCapacity: Int): AtomicArray<DataNode<Key, Value>?> = resizeLock.write {
        val oldData: AtomicArray<DataNode<Key, Value>?>? = data.value

        data.value = atomicArrayOfNulls(newCapacity)
        val oldSize = actualSize.value

        oldData ?: return@write data.value!!

        for (i in 0 until oldSize) {
            val nodeReference: AtomicRef<DataNode<Key, Value>?> = oldData[i]
            val node: DataNode<Key, Value> = nodeReference.value ?: continue
            unsafePut(node)
        }

        return@write data.value!!
    }

    private inline fun unsafeGet(key: Key): Value? {
        val array: AtomicArray<DataNode<Key, Value>?> = data.value ?: resize(INITIAL_CAPACITY)
        val capacity = capacity.value

        var index = key.hashCode() % capacity
        while (true) {
            val currentNode: DataNode<Key, Value>? = array[index].value

            if (currentNode != null && key == currentNode.key) {
                return currentNode.value
            }

            index = (index + 1) % capacity
        }
    }

    private inline fun unsafePut(node: DataNode<Key, Value>): Value? {
        val array: AtomicArray<DataNode<Key, Value>?> = data.value ?: resize(INITIAL_CAPACITY)
        val capacity = capacity.value
        val key = node.key!!
        val nodeToInsert = if (node.value != null) node else EmptyNode

        val currentHash = key.hashCode()
        var index = currentHash % capacity
        while (true) {
            val currentNode: DataNode<Key, Value>? = array[index].value

            /**
             * Empty, removed or replace.
             */
            if (currentNode == null || currentNode === EmptyNode || currentNode.key == key) {
                if (!array[index].compareAndSet(currentNode, nodeToInsert)) continue

                actualSize.incrementAndGet()
                return currentNode?.value
            }

            index = (index + 1) % capacity
        }
    }

    private fun loadFactor(): Float = actualSize.value.toFloat() / capacity.value
}

private class DataNode<Key, Value>(val key: Key?, val value: Value?)
