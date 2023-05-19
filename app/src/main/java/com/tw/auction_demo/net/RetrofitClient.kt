package com.tw.auction_demo.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://paiyipai.com/"
private const val TIME_OUT = 60L

class RetrofitClient private constructor() {
    companion object {
        val get by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            return@lazy RetrofitClient()
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(RemoteBFFService())
        .build()

    internal val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
}

object AuctionService : AuctionApi by RetrofitClient.get.retrofit.create(AuctionApi::class.java)
