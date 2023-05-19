package com.tw.auction_demo.net

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.lang.Thread.sleep

/**
 * Creates an OkHttp Interceptor that parses a request to dynamically
 */
class RemoteBFFService : Interceptor {

    companion object {
        const val VALUE_DEFAULT = "default.json"
        const val COMBINE_KEY_LIST = "formId"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var resourceFileContent: String? = null
        val operationName = request.url.pathSegments.last()+"/$VALUE_DEFAULT"
        run getContent@{
                val currentContent = readResourceFile(operationName)
                if (currentContent != null) {
                    resourceFileContent = currentContent
                    return@getContent
                }
        }

        return if (resourceFileContent == null) {
            chain.proceed(request)
        } else {
            Response.Builder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message("Mock response")
                .body(
                    resourceFileContent!!
                        .toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .request(request)
                .build()
        }
    }

    private fun readResourceFile(fileName: String) =
        javaClass.classLoader
            ?.getResourceAsStream(fileName)
            ?.bufferedReader()
            ?.readText()

    private fun JsonElement.toMap(
        map: MutableMap<String, String>,
        key: String?
    ): MutableMap<String, String> {
        when (this) {
            is JsonArray -> {
                this.forEach { element ->
                    element.toMap(map, null)
                }
            }

            is JsonObject -> {
                this.keys.forEach { key ->
                    this[key]?.toMap(map, key)
                }
            }

            else -> {
                key?.let {
                    map[it] = this.toString().replace("\"", "")
                }
            }
        }
        return map
    }
}
