package com.tw.auction_demo.utils

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mockAuctionListModel = AuctionListModel(
    id = "5",
    name = "Designer Handbag",
    description = "Luxurious designer handbag",
    image = "https://example.com/images/handbag.jpg",
    startingPrice = 500.00,
    currentPrice = 600.00,
    bidCount = 6,
    startTime = "2023-05-18T12:30:00Z",
    endTime = "2023-05-19T18:30:00Z",
    status = "active",
    bidTimes = 2,
    price = 3
)

val mockAuctionDetailsModel = AuctionDetailModel(
    id = "1001",
    name = "name",
    description = "description",
    image = "image",
    startingPrice = 200.0,
    currentPrice = 200.0,
    bidCount = 3,
    startTime = "startTime",
    endTime = "endTime",
    status = "status",
    seller = "seller",
    bidTimes = 4,
    deposit = "bidTimes",
    depositStatus = "depositStatus",
)

val mockDepositsPayResultModel = DepositsPayResultModel(result = "已支付")

val DB_AUCTIONS = listOf(
    mockAuctionDetailsModel,
    mockAuctionDetailsModel.copy(id="1002"),
    mockAuctionDetailsModel.copy(id="1003")
)


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
