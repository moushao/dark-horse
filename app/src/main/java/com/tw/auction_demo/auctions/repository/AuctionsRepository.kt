package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.datasource.local.AuctionLocalDataSource
import com.tw.auction_demo.auctions.datasource.remote.AuctionRemoteDataSource
import com.tw.auction_demo.auctions.model.AuctionListModel

interface AuctionsRepository {
    suspend fun getAuctions(): Result<List<AuctionListModel>>
}
