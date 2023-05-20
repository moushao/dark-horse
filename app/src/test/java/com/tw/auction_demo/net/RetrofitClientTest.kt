package com.tw.auction_demo.net

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Assert.*
import org.junit.Test

class RetrofitClientTest {
    @Test
    fun testRetrofitClientTest() {
        val retrofit = RetrofitClient.get
        assertEquals("https://paiyipai.com/".toHttpUrl(), retrofit.retrofit.baseUrl())
    }
}
