package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.datasource.local.AuctionLocalDataSource
import com.tw.auction_demo.auctions.datasource.remote.AuctionRemoteDataSource

class AuctionsRepositoryImpl(
    private val remoteDataSource: AuctionRemoteDataSource,
    private val localDataSource: AuctionLocalDataSource
) : AuctionsRepository {
    override suspend fun getAuctions(): Result<List<AuctionListModel>> {
        return runCatching {
            remoteDataSource.getAuctions()
        }
    }
}
