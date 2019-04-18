package io.ktor.network.sockets

import io.ktor.network.selector.*
import java.net.*
import java.nio.channels.*

private fun SelectableChannel.nonBlocking() {
    configureBlocking(false)
}
