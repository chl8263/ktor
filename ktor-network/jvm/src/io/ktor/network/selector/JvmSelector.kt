package io.ktor.network.selector

import java.nio.channels.*

interface JvmSelectable : Selectable {
    val channel: SelectableChannel
}
