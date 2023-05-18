package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.datasource.local.AuctionLocalDataSource
import com.tw.auction_demo.auctions.datasource.remote.AuctionRemoteDataSource

class AuctionsRepository(
    private val remoteDataSource: AuctionRemoteDataSource,
    private val localDataSource: AuctionLocalDataSource
) {
    suspend fun getAuctions(): Result<List<AuctionModel>> {
        return runCatching {
            remoteDataSource.getAuctions()
        }
    }
}