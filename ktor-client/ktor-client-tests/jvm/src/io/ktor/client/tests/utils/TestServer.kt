package io.ktor.client.tests.utils

import ch.qos.logback.classic.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import kotlinx.io.core.*
import org.slf4j.*
import java.util.concurrent.*

private val DEFAULT_PORT: Int = 8080

internal fun startServer(): Closeable {
    val logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
    logger.level = Level.WARN

    val server = embeddedServer(Jetty, DEFAULT_PORT) {
        tests()
        benchmarks()
    }.start()

    return Closeable { server.stop(0L, 0L, TimeUnit.SECONDS) }
}

/**
 * Start server for tests.
 */
fun main() {
    startServer()
}
