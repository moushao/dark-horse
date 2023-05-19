package com.tw.auction_demo.auctions.datasource.remote

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.net.AuctionApi

interface AuctionRemoteDataSource {
    suspend fun getAuctions(): List<AuctionListModel>

    suspend fun getAuctionDetails(auctionId: String): AuctionDetailModel
}
