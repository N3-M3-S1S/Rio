package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.data.api.serialization.ApiLocalDateTimeDeserializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileLastCrawlDateTime(
    @Serializable(with = ApiLocalDateTimeDeserializer::class)
    @SerialName("last_crawled_at")
    val lastCrawlDateTIme: LocalDateTime
)
