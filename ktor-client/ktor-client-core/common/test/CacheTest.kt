package io.ktor.client.features.cache

import io.ktor.client.request.*
import io.ktor.client.tests.utils.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlin.test.*

class CacheTest {

    @Test
    fun testNoStore() = clientsTest {
        var storage: HttpCache.Config? = null
        config {
            install(HttpCache) {
                storage = this
            }
        }

        test { client ->
            val url = Url("$TEST_SERVER/cache/no-store")

            val first = client.get<String>(url)
            assertTrue(storage!!.privateStorage.findByUrl(url).isEmpty())
            assertTrue(storage!!.publicStorage.findByUrl(url).isEmpty())

            val second = client.get<String>(url)
            assertTrue(storage!!.privateStorage.findByUrl(url).isEmpty())
            assertTrue(storage!!.publicStorage.findByUrl(url).isEmpty())
        }
    }

    @Test
    fun testNoCache() = clientsTest {
        var storage: HttpCache.Config? = null
        config {
            install(HttpCache) {
                storage = this
            }
        }

        test { client ->
            val url = Url("$TEST_SERVER/cache/no-cache")

            val first = client.get<String>(url)
            assertEquals(1, storage!!.privateStorage.findByUrl(url).size)

            val second = client.get<String>(url)
            assertEquals(1, storage!!.privateStorage.findByUrl(url).size)

            assertNotEquals(first, second)
        }
    }

    @Test
    fun testETagCache() = clientsTest {
        var storage: HttpCache.Config? = null
        config {
            install(HttpCache) {
                storage = this
            }
        }

        test { client ->
            val url = Url("$TEST_SERVER/cache/etag")

            val first = client.get<String>(url)
            assertEquals(1, storage!!.privateStorage.findByUrl(url).size)

            val second = client.get<String>(url)
            assertEquals(1, storage!!.privateStorage.findByUrl(url).size)

            assertNotEquals(first, second)
        }
    }

    @Test
    fun testMaxAge() = clientsTest {
        var storage: HttpCache.Config? = null
        config {
            install(HttpCache) {
                storage = this
            }
        }

        test { client ->
            val url = Url("$TEST_SERVER/cache/max-age")

            val first = client.get<String>(url)
            assertEquals(1, storage!!.privateStorage.findByUrl(url).size)

            val second = client.get<String>(url)

            assertEquals(first, second)
            delay(5000)

            val third = client.get<String>(url)
            assertNotEquals(first, third)
        }
    }

    @Test
    fun testPublicCache() {
    }

    @Test
    fun testPrivateCache() {
    }
}
