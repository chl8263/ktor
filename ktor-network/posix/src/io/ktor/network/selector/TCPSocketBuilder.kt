package io.ktor.network.selector

import io.ktor.network.sockets.*
import io.ktor.network.util.*

/**
 * TCP socket builder
 */
@Suppress("PublicApiImplicitType")
actual class TCPSocketBuilder(override var options: SocketOptions) : Configurable<TCPSocketBuilder, SocketOptions> {
    /**
     * Connect to [hostname] and [port].
     */
    actual suspend fun connect(
        hostname: String,
        port: Int,
        configure: SocketOptions.TCPClientSocketOptions.() -> Unit
    ): Socket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Bind server socket at [port] to listen to [hostname].
     */
    actual fun bind(
        hostname: String,
        port: Int,
        configure: SocketOptions.AcceptorOptions.() -> Unit
    ): ServerSocket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Connect to [remoteAddress].
     */
    actual suspend fun connect(
        remoteAddress: NetworkAddress,
        configure: SocketOptions.TCPClientSocketOptions.() -> Unit
    ): Socket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Bind server socket to listen to [localAddress].
     */
    actual fun bind(
        localAddress: NetworkAddress?,
        configure: SocketOptions.AcceptorOptions.() -> Unit
    ): ServerSocket {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
