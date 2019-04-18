package io.ktor.network.selector

import io.ktor.network.sockets.*
import io.ktor.network.util.*

/**
 * UDP socket builder
 */
actual class UDPSocketBuilder(override var options: SocketOptions.UDPSocketOptions) :
    Configurable<UDPSocketBuilder, SocketOptions.UDPSocketOptions> {
    /**
     * Bind server socket to listen to [localAddress]
     */
    actual fun bind(
        localAddress: NetworkAddress?,
        configure: SocketOptions.UDPSocketOptions.() -> Unit
    ): BoundDatagramSocket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Create a datagram socket to listen datagrams at [localAddress] and set to [remoteAddress]
     */
    actual fun connect(
        remoteAddress: NetworkAddress,
        localAddress: NetworkAddress?,
        configure: SocketOptions.UDPSocketOptions.() -> Unit
    ): ConnectedDatagramSocket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

internal actual fun UDPSocketBuilder(
    selector: SelectorManager,
    options: SocketOptions.UDPSocketOptions
): UDPSocketBuilder {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

internal actual fun TCPSocketBuilder(
    selector: SelectorManager,
    options: SocketOptions
): TCPSocketBuilder {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}
