package com.tw.auction_demo.auctions.datasource.remote

import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.net.AuctionApi

class AuctionRemoteDataSource(private val auctionApi: AuctionApi) {

    suspend fun getAuctions(): List<AuctionModel> {
        val response = auctionApi.getAuctions()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }
}