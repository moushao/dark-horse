package com.tw.auction_demo.net

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuctionApi {
    @Throws(Exception::class)
    @GET("/auctions")
    suspend fun getAuctions(): Response<List<AuctionListModel>>

    @GET("/auction/{aid}")
    suspend fun getAuctionDetails(@Path("aid") auctionId: String): Response<AuctionDetailModel>

    @FormUrlEncoded
    @POST("/auctions/{aid}/deposits/payment-confirmation")
    suspend fun depositsPay(
        @Path("aid") auctionId: String,
        @Field("amount") amount: String
    ): Response<DepositsPayResultModel>

    @GET("/auctions/{aid}/deposits/payment-confirmation")
    suspend fun checkDepositPayResult(): Response<List<DepositsPayResultModel>>
}
