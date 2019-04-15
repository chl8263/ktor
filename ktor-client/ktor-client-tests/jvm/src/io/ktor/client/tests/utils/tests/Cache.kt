package io.ktor.client.tests.utils.tests

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.atomicfu.*

val counter = atomic(0)

fun Application.cacheTestServer() {
    routing {
        route("/cache") {
            get("/reset") {
                counter.value = 0
                call.respondText("")
            }
            get("/no-cache") {
                val value = counter.incrementAndGet()
                call.response.cacheControl(CacheControl.NoCache(null))
                call.respondText("$value")
            }
            get("/no-store") {
                val value = counter.incrementAndGet()
                call.response.cacheControl(CacheControl.NoStore(null))
                call.respondText("$value")
            }
            get("/max-age") {
                val value = counter.incrementAndGet()
                call.response.cacheControl(CacheControl.MaxAge(5))
                call.respondText("$value")
            }

            /**
             * Return same etag for first 2 responses.
             */
            get("/etag") {
                var current = counter.value
                val etag = if (current < 2) "0" else "1"

                current = counter.incrementAndGet()
                @Suppress("DEPRECATION")
                call.withETag(etag) {
                    call.respondText(current.toString())
                }
            }
        }
    }
}
