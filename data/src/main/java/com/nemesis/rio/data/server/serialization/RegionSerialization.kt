package com.nemesis.rio.data.server.serialization

import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.Region.*

internal object RegionSerialization {
    private val regionToJsonValue = enumValues<Region>().associateWith { region ->
        when (region) {
            EU -> "eu"
            US -> "us"
            KR -> "kr"
            TW -> "tw"
        }
    }

    fun getRegionFromJsonValue(regionJsonValue: String) =
        regionToJsonValue.filter { it.value == regionJsonValue }.keys.first()

    fun getJsonValueForRegion(region: Region) =
        regionToJsonValue.getValue(region)
}
