package io.ktor.util.collections

import io.ktor.util.*
import java.util.concurrent.*

@InternalAPI
actual class ConcurrentMap<Key, Value> : MutableMap<Key, Value> by ConcurrentHashMap<Key, Value>()
