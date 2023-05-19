package com.tw.auction_demo.net

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AuctionApi {
    @Throws(Exception::class)
    @GET("/auctions")
    suspend fun getAuctions(): Response<List<AuctionListModel>>

    @GET("/auction/{aid}")
    suspend fun getAuctionDetails(@Path("aid") auctionId: String): Response<AuctionDetailModel>
}
