package io.ktor.client.features.cache

import io.ktor.http.*

internal class UnlimitedCacheStorage : HttpCacheStorage() {
    override fun store(url: Url, value: HttpCacheEntry) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(url: Url, varyKeys: Map<String, String>): HttpCacheEntry? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByUrl(url: Url): List<HttpCacheEntry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
