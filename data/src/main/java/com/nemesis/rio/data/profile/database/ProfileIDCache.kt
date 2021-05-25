package com.nemesis.rio.data.profile.database

import androidx.core.util.lruCache
import com.nemesis.rio.domain.profile.Profile

class ProfileIDCache(size: Int = 5) {
    private val cache = lruCache<Profile, Long>(size)

    fun add(profile: Profile, id: Long) {
        cache.put(profile, id)
    }

    fun get(profile: Profile): Long? = cache[profile]

    fun remove(profile: Profile) {
        cache.remove(profile)
    }

    fun clear() {
        cache.evictAll()
    }
}
