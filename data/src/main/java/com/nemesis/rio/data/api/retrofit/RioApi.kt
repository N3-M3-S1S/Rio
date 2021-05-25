package com.nemesis.rio.data.api.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nemesis.rio.data.api.refresh.ProfileLastCrawlDateTime
import com.nemesis.rio.data.api.search.CharacterSearchResponse
import com.nemesis.rio.data.api.search.GuildSearchResponse
import com.nemesis.rio.data.profile.api.ProfileSearchParameters
import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.guild.api.GuildSearchFields
import com.nemesis.rio.data.server.api.ServerParameters
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RioApi {
    companion object {

        @OptIn(ExperimentalSerializationApi::class)
        fun create(): RioApi = with(Retrofit.Builder()) {
            client(createHttpClient())
            baseUrl("https://raider.io/api/v1/")
            addConverterFactory(Json {
                isLenient = true
                ignoreUnknownKeys = true
            }.asConverterFactory(MediaType.get("application/json")))
            addConverterFactory(RegionConverterFactory())
            build()
        }.create(RioApi::class.java)

        private fun createHttpClient() = with(OkHttpClient.Builder()) {
            connectTimeout(30L, TimeUnit.SECONDS)
            addInterceptor(RealmNameQueryParameterInterceptor())
            addInterceptor(CharacterNameQueryParameterCapitalizeInterceptor())
            build()
        }
    }

    @GET("characters/profile")
    suspend fun getCharacterLastCrawlDateTime(
        @Query(ProfileSearchParameters.NAME) name: String,
        @Query(ServerParameters.REGION) region: Region,
        @Query(ServerParameters.REALM) realm: Realm,
    ): ProfileLastCrawlDateTime

    @GET("guilds/profile")
    suspend fun getGuildLastCrawlDateTime(
        @Query(ProfileSearchParameters.NAME) name: String,
        @Query(ServerParameters.REGION) region: Region,
        @Query(ServerParameters.REALM) realm: Realm,
    ): ProfileLastCrawlDateTime

    @GET("characters/profile")
    suspend fun searchCharacter(
        @Query(ProfileSearchParameters.NAME) name: String,
        @Query(ServerParameters.REGION) region: Region,
        @Query(ServerParameters.REALM) realm: Realm,
        @Query(ProfileSearchParameters.FIELDS, encoded = true) fields: CharacterSearchFields
    ): CharacterSearchResponse

    @GET("guilds/profile")
    suspend fun searchGuild(
        @Query(ProfileSearchParameters.NAME) name: String,
        @Query(ServerParameters.REGION) region: Region,
        @Query(ServerParameters.REALM) realm: Realm,
        @Query(ProfileSearchParameters.FIELDS, encoded = true) fields: GuildSearchFields
    ): GuildSearchResponse
}
