package io.ktor.network.selector

import kotlinx.io.core.*
import java.nio.channels.spi.*

/**
 * Creates a NIO entity via [create] and calls [setup] on it. If any exception happens then the entity will be closed
 * and an exception will be propagated.
 */
inline fun <C : Closeable, R> SelectorManagerSupport.buildOrClose(
    create: SelectorProvider.() -> C,
    setup: C.() -> R
): R {
    while (true) {
        val result = create(provider)

        try {
            return setup(result)
        } catch (cause: Throwable) {
            result.close()
            throw cause
        }
    }
}
