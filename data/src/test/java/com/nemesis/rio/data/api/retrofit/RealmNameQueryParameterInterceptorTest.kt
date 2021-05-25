package com.nemesis.rio.data.api.retrofit

import com.nemesis.rio.data.server.api.ServerParameters
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.test.Test
import kotlin.test.assertEquals

class RealmNameQueryParameterInterceptorTest {

    @Test
    fun `interceptor replaces realm name in response with realm name from query parameter`() {
        val mockWebServer = MockWebServer().apply { start() }

        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(RealmNameQueryParameterInterceptor()).build()

        val realmNameQueryParameter = "requestRealm"
        val requestUrl = mockWebServer.url("/").newBuilder()
            .addQueryParameter(ServerParameters.REALM, realmNameQueryParameter).build()
        val request = Request.Builder().url(requestUrl).build()

        val responseBody = buildJsonObject {
            put(ServerParameters.REALM, JsonPrimitive("responseRealm"))
        }.toString()
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        val response = okHttpClient.newCall(request).execute()
        val responseJson = Json.decodeFromString<JsonObject>(response.body!!.string())
        val responseRealmName = responseJson[ServerParameters.REALM]?.jsonPrimitive?.content

        assertEquals(realmNameQueryParameter, responseRealmName)
    }
}
