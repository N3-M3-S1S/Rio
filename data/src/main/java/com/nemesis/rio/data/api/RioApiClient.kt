package com.nemesis.rio.data.api

import com.nemesis.rio.data.api.retrofit.RioApi
import com.nemesis.rio.data.api.search.CharacterSearchResponse
import com.nemesis.rio.data.api.search.GuildSearchResponse
import com.nemesis.rio.data.api.serialization.ApiSpecificMessageDeserializer
import com.nemesis.rio.data.connection.NetworkConnectionStatus
import com.nemesis.rio.data.connection.NotConnectedToNetworkException
import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.guild.api.GuildSearchFields
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class RioApiClient(
    private val rioApi: RioApi,
    private val networkConnectionStatus: NetworkConnectionStatus,
    private val characterSearchFieldsFactory: CharacterSearchFields.Factory,
    private val guildSearchFieldsFactory: GuildSearchFields.Factory
) {
    private val profileNotFoundMessageRegex =
        "^Could not find requested (character|guild)\$".toRegex()

    suspend fun getGuildLastCrawledDateTime(
        name: String,
        region: Region,
        realm: Realm
    ): LocalDateTime? = profileSearchQuery {
        rioApi.getGuildLastCrawlDateTime(name, region, realm).lastCrawlDateTIme
    }

    suspend fun getCharacterLastCrawlDateTime(
        name: String,
        region: Region,
        realm: Realm
    ): LocalDateTime? = profileSearchQuery {
        rioApi.getCharacterLastCrawlDateTime(name, region, realm).lastCrawlDateTIme
    }

    suspend fun searchCharacter(
        name: String,
        region: Region,
        realm: Realm
    ): CharacterSearchResponse? = profileSearchQuery {
        rioApi.searchCharacter(
            name,
            region,
            realm,
            characterSearchFieldsFactory.create()
        )
    }

    suspend fun searchGuild(
        name: String,
        region: Region,
        realm: Realm
    ): GuildSearchResponse? = profileSearchQuery {
        rioApi.searchGuild(name, region, realm, guildSearchFieldsFactory.create())
    }

    private suspend fun <P> profileSearchQuery(block: suspend () -> P) = runCatching {
        ensureConnectedToNetwork()
        block()
    }.getOrElse(::handleProfileSearchQueryException)

    private fun ensureConnectedToNetwork() {
        if (!networkConnectionStatus.isDeviceConnectedToNetwork()) {
            throw NotConnectedToNetworkException()
        }
    }

    private fun handleProfileSearchQueryException(exception: Throwable): Nothing? {
        if (exception is HttpException && apiException(exception)) {
            val apiExceptionMessage = getApiExceptionMessage(exception)
            if (profileNotFoundMessage(apiExceptionMessage)) {
                return null
            } else {
                throw Exception(apiExceptionMessage, exception)
            }
        } else {
            throw exception
        }
    }

    private fun apiException(exception: HttpException): Boolean {
        return exception.response()?.errorBody()?.contentType()?.subtype()?.equals("json")
            ?: false // api returns api-specific errors in json
    }

    private fun getApiExceptionMessage(apiException: HttpException): String {
        val apiErrorBodyString = apiException.response()!!.errorBody()!!.string()
        return Json.decodeFromString(ApiSpecificMessageDeserializer, apiErrorBodyString)
    }

    private fun profileNotFoundMessage(apiExceptionMessage: String) =
        apiExceptionMessage.matches(profileNotFoundMessageRegex)
}
