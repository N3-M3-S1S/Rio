package com.nemesis.rio.data.api.retrofit

import com.nemesis.rio.data.profile.api.ProfileSearchParameters
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterNameQueryParameterCapitalizeInterceptorTest {

    @Test
    fun `interceptor capitalizes character name in url query parameter`() {
        val mockWebServer = MockWebServer().apply {
            start()
            enqueue(MockResponse())
        }

        val characterNameQueryParameter = "characTER"
        val requestUrl = mockWebServer.url("/").newBuilder()
            .addPathSegment("characters")
            .addQueryParameter(ProfileSearchParameters.NAME, characterNameQueryParameter)
            .build()
        val request = Request.Builder().url(requestUrl).build()

        val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(CharacterNameQueryParameterCapitalizeInterceptor()).build()

        okHttpClient.newCall(request).execute()

        val expectedCharacterName = "Character"

        val requestAfterInterceptor = mockWebServer.takeRequest()
        val characterNameQueryParameterAfterInterceptor =
            requestAfterInterceptor.requestUrl?.queryParameter(ProfileSearchParameters.NAME)

        assertEquals(expectedCharacterName, characterNameQueryParameterAfterInterceptor)
    }
}
