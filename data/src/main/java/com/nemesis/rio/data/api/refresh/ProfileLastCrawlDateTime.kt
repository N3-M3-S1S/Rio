package com.nemesis.rio.data.api.refresh

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileLastCrawlDateTime(@SerialName("last_crawled_at") val lastCrawled: Instant)
