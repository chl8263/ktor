package io.ktor.network.selector

import io.ktor.util.*
import java.nio.channels.*

/**
 * Select interest kind
 * @property flag to be set in NIO selector
 */
@Suppress("KDocMissingDocumentation")
@KtorExperimentalAPI
actual enum class SelectInterest(actual val flag: Int) {
    READ(SelectionKey.OP_READ),
    WRITE(SelectionKey.OP_WRITE),
    ACCEPT(SelectionKey.OP_ACCEPT),
    CONNECT(SelectionKey.OP_CONNECT);

    actual companion object {
        actual val AllInterests: Array<SelectInterest>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        /**
         * interest's flags in enum entry order
         */
        actual val size: Int
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

        actual val flags: Array<Int>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    }

}
