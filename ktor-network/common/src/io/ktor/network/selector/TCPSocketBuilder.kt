package io.ktor.network.selector

import io.ktor.network.sockets.*
import io.ktor.network.util.*

internal expect fun TCPSocketBuilder(
    selector: SelectorManager,
    options: SocketOptions
): TCPSocketBuilder

/**
 * TCP socket builder
 */
@Suppress("PublicApiImplicitType")
expect class TCPSocketBuilder: Configurable<TCPSocketBuilder, SocketOptions> {
    /**
     * Connect to [hostname] and [port].
     */
    suspend fun connect(
        hostname: String,
        port: Int,
        configure: SocketOptions.TCPClientSocketOptions.() -> Unit = {}
    ): Socket

    /**
     * Bind server socket at [port] to listen to [hostname].
     */
    fun bind(
        hostname: String = "0.0.0.0",
        port: Int = 0,
        configure: SocketOptions.AcceptorOptions.() -> Unit = {}
    ): ServerSocket

    /**
     * Connect to [remoteAddress].
     */
    suspend fun connect(
        remoteAddress: NetworkAddress,
        configure: SocketOptions.TCPClientSocketOptions.() -> Unit = {}
    ): Socket

    /**
     * Bind server socket to listen to [localAddress].
     */
    fun bind(
        localAddress: NetworkAddress? = null,
        configure: SocketOptions.AcceptorOptions.() -> Unit = {}
    ): ServerSocket
}
