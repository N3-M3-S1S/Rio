package com.nemesis.rio.data.api.retrofit

import com.nemesis.rio.data.server.api.ServerParameters
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import okhttp3.*
import java.net.HttpURLConnection
import java.net.URLDecoder

/**
 * Raider.io api returns realm name in english even if input was in other language
 * for example, it is possible to search for the same realm using russian name 'Ревущий Фьорд' or english name 'Howling Fjord', but response will contain realm name in english regardless of input
 * This class replaces realm name in response with the name that was used for query if response is success and request has realm name query parameter.
 */
internal class RealmNameQueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url()
        val originalResponse = chain.proceed(request)
        return if (isResponseSuccess(originalResponse) and
            requestUrlHasRealmNameQueryParameter(requestUrl)
        ) {
            val realmNameQueryParameter = getRealmNameQueryParameter(requestUrl)
            buildResponseWithRealmNameQueryParameter(originalResponse, realmNameQueryParameter)
        } else {
            originalResponse
        }
    }

    private fun requestUrlHasRealmNameQueryParameter(requestUrl: HttpUrl): Boolean =
        requestUrl.queryParameterNames().contains(ServerParameters.REALM)

    private fun isResponseSuccess(response: Response): Boolean =
        response.code() == HttpURLConnection.HTTP_OK

    private fun getRealmNameQueryParameter(requestUrl: HttpUrl): String =
        requestUrl.queryParameter(ServerParameters.REALM)
            ?: error("Field '${ServerParameters.REALM}' is missing in request url: $requestUrl")

    private fun buildResponseWithRealmNameQueryParameter(
        originalResponse: Response,
        realmNameQueryParameter: String
    ): Response {
        val originalResponseJsonObject = buildJsonObjectWithResponse(originalResponse)
        val jsonObjectWithReplacedRealmName =
            replaceRealmNameInJsonObject(originalResponseJsonObject, realmNameQueryParameter)
        return buildResponseWithJsonObject(originalResponse, jsonObjectWithReplacedRealmName)
    }

    private fun buildJsonObjectWithResponse(response: Response): JsonObject {
        return Json.parseToJsonElement(response.body()!!.string()).jsonObject
    }

    private fun replaceRealmNameInJsonObject(
        jsonObject: JsonObject,
        newRealmName: String
    ): JsonObject {
        val mutableJsonObjectContent = jsonObject.toMutableMap()
        mutableJsonObjectContent[ServerParameters.REALM] =
            JsonPrimitive(URLDecoder.decode(newRealmName, "UTF-8"))
        return JsonObject(mutableJsonObjectContent)
    }

    private fun buildResponseWithJsonObject(
        response: Response,
        jsonObject: JsonObject
    ): Response = response.newBuilder()
        .body(
            ResponseBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )
        ).build()
}
