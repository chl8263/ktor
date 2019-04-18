package io.ktor.network.selector

import io.ktor.network.sockets.*

/**
 * Start building a socket
 */
fun aSocket(selector: SelectorManager): SocketBuilder = SocketBuilder(selector, SocketOptions.create())

/**
 * Socket builder
 */
@Suppress("PublicApiImplicitType", "unused")
class SocketBuilder internal constructor(
    private val selector: SelectorManager,
    override var options: SocketOptions
) : Configurable<SocketBuilder, SocketOptions> {

    /**
     * Build TCP socket
     */
    fun tcp(): TCPSocketBuilder = TCPSocketBuilder(selector, options.peer())

    /**
     * Build UDP socket
     */
    fun udp(): UDPSocketBuilder = UDPSocketBuilder(selector, options.peer().udp())
}

/**
 * Set TCP_NODELAY socket option to disable the Nagle algorithm.
 */
fun <T : Configurable<T, *>> T.tcpNoDelay(): T {
    return configure {
        if (this is SocketOptions.TCPClientSocketOptions) {
            noDelay = true
        }
    }
}

/**
 * Represent a configurable socket
 */
interface Configurable<out T : Configurable<T, Options>, Options : SocketOptions> {
    /**
     * Current socket options
     */
    var options: Options

    /**
     * Configure socket options in [block] function
     */
    fun configure(block: Options.() -> Unit): T {
        @Suppress("UNCHECKED_CAST")
        val newOptions = options.copy() as Options

        block(newOptions)
        options = newOptions

        @Suppress("UNCHECKED_CAST")
        return this as T
    }
}
