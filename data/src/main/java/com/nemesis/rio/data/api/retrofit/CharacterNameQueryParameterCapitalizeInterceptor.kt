package com.nemesis.rio.data.api.retrofit

import com.nemesis.rio.data.profile.api.ProfileSearchParameters
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

/**
 * Raider.io API says that a character's name is not case sensitive, but it is for Russian language (maybe for other languages too, didn't test, but English language works as intended).
 * This interceptor replaces a character's name in a query parameter with the same name but first character is upper case and rest is lower case if the request is character request.
 */
internal class CharacterNameQueryParameterCapitalizeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalRequestUrl = originalRequest.url()
        val requestToProceed =
            if (isCharacterRequest(originalRequestUrl)) {
                buildRequestWithCapitalizedCharacterNameQueryParameter(originalRequestUrl)
            } else {
                originalRequest
            }
        return chain.proceed(requestToProceed)
    }

    private fun isCharacterRequest(url: HttpUrl): Boolean =
        url.pathSegments().contains("characters")

    private fun buildRequestWithCapitalizedCharacterNameQueryParameter(
        url: HttpUrl
    ): Request {
        val characterName = getCharacterNameFromUrl(url)
        val capitalizedCharacterName = capitalizeCharacterName(characterName)
        val urlForRequest =
            setCharacterNameQueryParameterToUrl(
                url,
                capitalizedCharacterName
            )
        return buildRequestWithUrl(urlForRequest)
    }

    private fun getCharacterNameFromUrl(url: HttpUrl): String =
        url.queryParameter(ProfileSearchParameters.NAME)
            ?: error("Missing '${ProfileSearchParameters.NAME}' attribute in character request url: $url")

    @Suppress("DEPRECATION") // function String.capitalize(Locale) should not be deprecated, may be it's a bug
    private fun capitalizeCharacterName(characterName: String): String {
        val currentDeviceLocale = Locale.getDefault()
        return characterName.lowercase(currentDeviceLocale).capitalize(Locale.getDefault())
    }

    private fun setCharacterNameQueryParameterToUrl(
        url: HttpUrl,
        characterName: String
    ): HttpUrl = url
        .newBuilder()
        .setQueryParameter(ProfileSearchParameters.NAME, characterName)
        .build()

    private fun buildRequestWithUrl(url: HttpUrl): Request =
        Request.Builder().url(url).build()
}
