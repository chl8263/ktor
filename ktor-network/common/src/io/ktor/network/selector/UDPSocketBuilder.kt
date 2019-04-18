package io.ktor.network.selector

import io.ktor.network.sockets.*
import io.ktor.network.util.*

internal expect fun UDPSocketBuilder(
    selector: SelectorManager,
    options: SocketOptions.UDPSocketOptions
): UDPSocketBuilder

/**
 * UDP socket builder
 */
expect class UDPSocketBuilder :
    Configurable<UDPSocketBuilder, SocketOptions.UDPSocketOptions> {
    /**
     * Bind server socket to listen to [localAddress]
     */
    fun bind(
        localAddress: NetworkAddress? = null,
        configure: SocketOptions.UDPSocketOptions.() -> Unit = {}
    ): BoundDatagramSocket

    /**
     * Create a datagram socket to listen datagrams at [localAddress] and set to [remoteAddress]
     */
    fun connect(
        remoteAddress: NetworkAddress, localAddress: NetworkAddress? = null,
        configure: SocketOptions.UDPSocketOptions.() -> Unit = {}
    ): ConnectedDatagramSocket
}
