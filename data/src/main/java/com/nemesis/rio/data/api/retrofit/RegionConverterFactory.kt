package com.nemesis.rio.data.api.retrofit

import com.nemesis.rio.data.server.serialization.RegionSerialization
import com.nemesis.rio.domain.server.Region
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class RegionConverterFactory : Converter.Factory() {

    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<*, String>? {
        return if (type is Enum<*> && type == Region::class.java) {
            Converter { region: Region -> RegionSerialization.getJsonValueForRegion(region) }
        } else {
            super.stringConverter(type, annotations, retrofit)
        }
    }
}
