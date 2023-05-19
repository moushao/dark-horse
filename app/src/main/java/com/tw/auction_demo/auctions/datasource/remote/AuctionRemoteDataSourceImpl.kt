package com.tw.auction_demo.auctions.datasource.remote

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.net.AuctionApi

class AuctionRemoteDataSourceImpl(private val auctionApi: AuctionApi) : AuctionRemoteDataSource {

    override suspend fun getAuctions(): List<AuctionListModel> {
        val response = auctionApi.getAuctions()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    override suspend fun getAuctionDetails(auctionId:String): AuctionDetailModel {
        val response = auctionApi.getAuctionDetails("")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }
}
