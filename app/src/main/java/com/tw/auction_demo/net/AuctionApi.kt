package com.tw.auction_demo.net

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AuctionApi {
    @Throws(Exception::class)
    @GET("/auctions")
    suspend fun getAuctions(): Response<List<AuctionModel>>

    @GET("/auction/{aid}")
    suspend fun getAuctionDetails(@Path("aid") auctionId: String): Response<AuctionDetailModel>
}