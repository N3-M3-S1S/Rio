package com.nemesis.rio.data.api

import com.nemesis.rio.data.api.retrofit.RioApi
import com.nemesis.rio.data.api.search.CharacterSearchResponse
import com.nemesis.rio.data.api.search.GuildSearchResponse
import com.nemesis.rio.data.api.serialization.ApiSpecificMessageDeserializer
import com.nemesis.rio.data.connection.NetworkConnectionStatus
import com.nemesis.rio.data.connection.NotConnectedToNetworkException
import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.guild.api.GuildSearchFields
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import timber.log.Timber
import java.net.HttpURLConnection

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
        doIfExceptionIsApiException(exception) { apiHttpException ->
            val apiExceptionMessage = getApiExceptionMessage(apiHttpException)
            if (profileNotFoundMessage(apiExceptionMessage)) {
                return null
            } else {
                throw Exception(apiExceptionMessage, exception)
            }
        }
        throw exception
    }

    private fun getApiExceptionMessage(apiException: HttpException): String {
        val apiErrorBodyString = apiException.response()!!.errorBody()!!.string()
        return Json.decodeFromString(ApiSpecificMessageDeserializer, apiErrorBodyString)
    }

    private fun profileNotFoundMessage(apiExceptionMessage: String) =
        apiExceptionMessage.matches(profileNotFoundMessageRegex)

    suspend fun getMythicPlusScoresWithColors(seasonApiValue: String): Map<MythicPlusScore, HexColor> =
        runCatching {
            ensureConnectedToNetwork()
            rioApi.getMythicPlusScoreColors(seasonApiValue).associate { it.score to it.hexColor }
        }.getOrElse(::handleMythicPlusScoreColorsException)

    private fun handleMythicPlusScoreColorsException(exception: Throwable): Map<MythicPlusScore, HexColor> {
        doIfExceptionIsApiException(exception) { apiHttpException ->
            // internal error happens if there are no score colors for a season
            return if (apiHttpException.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                Timber.e("Internal server error occurred, return empty score colors list")
                emptyMap()
            } else {
                val apiExceptionMessage = getApiExceptionMessage(apiHttpException)
                throw Exception(apiExceptionMessage, exception)
            }
        }
        throw exception
    }

    private inline fun doIfExceptionIsApiException(
        exception: Throwable,
        block: (apiHttpException: HttpException) -> Unit
    ) {
        if (exception is HttpException && apiException(exception)) {
            block(exception)
        }
    }

    private fun apiException(exception: HttpException): Boolean {
        return exception.response()?.errorBody()?.contentType()?.subtype()?.equals("json")
            ?: false // api returns api-specific errors in json
    }

}
