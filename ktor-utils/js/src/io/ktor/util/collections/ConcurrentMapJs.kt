package io.ktor.util.collections

actual class ConcurrentMap<Key, Value> : MutableMap<Key, Value> by mutableMapOf()
