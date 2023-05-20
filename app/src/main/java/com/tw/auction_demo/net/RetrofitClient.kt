package com.tw.auction_demo.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private const val BASE_URL = "https://paiyipai.com/"
private const val TIME_OUT = 60L

class RetrofitClient private constructor() {
    companion object {
        val get by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            return@lazy RetrofitClient()
        }
    }

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    private val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, trustAllCerts, null)
    }

    private val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory


    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(RemoteBFFService())
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()

    internal val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
}

object AuctionService : AuctionApi by RetrofitClient.get.retrofit.create(AuctionApi::class.java)
