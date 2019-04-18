package io.ktor.network.selector

import io.ktor.util.*

/**
 * Select interest kind
 * @property flag to be set in NIO selector
 */
@Suppress("KDocMissingDocumentation")
@KtorExperimentalAPI
actual enum class SelectInterest {
    READ, WRITE, ACCEPT, CONNECT;

    actual companion object {
        actual val AllInterests: Array<SelectInterest>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        /**
         * interest's flags in enum entry order
         */
        actual val size: Int
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    }

}
