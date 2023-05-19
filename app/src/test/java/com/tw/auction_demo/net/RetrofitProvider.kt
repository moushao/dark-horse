package com.tw.auction_demo.net

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getTestRetrofit(
    baseUrl: HttpUrl
): Retrofit {
    val client = OkHttpClient.Builder()
        .hostnameVerifier { _, _ -> true }
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun mockResponse(
    classLoader: ClassLoader,
    filePath: String
): MockResponse {
    val mockResponseBody =classLoader.getResourceAsStream(filePath)
        ?.bufferedReader()
        .use { it?.readText() } ?: "{}"
    return MockResponse()
        .setResponseCode(200)
        .setBody(mockResponseBody)
}
