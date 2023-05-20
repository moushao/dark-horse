package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.datasource.database.LocalDataSource
import com.tw.auction_demo.auctions.datasource.remote.RemoteDataSource
import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

class AuctionsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AuctionsRepository {
    override suspend fun getAuctions(): Result<List<AuctionListModel>> {
        return runCatching {
            remoteDataSource.getAuctions()
        }
    }

    override suspend fun getAuctionDetails(auctionId: String): Result<AuctionDetailModel> {

        return runCatching {
            try {
                remoteDataSource.getAuctionDetails(auctionId).apply {
                    localDataSource.saveAuction(this)
                }
            } catch (e: Exception) {
                localDataSource.getAuction(auctionId)
            }
        }
    }

    override suspend fun depositsPay(auction: AuctionDetailModel): Result<DepositsPayResultModel> {
        return runCatching {
            val result = remoteDataSource.depositsPay(auction.id, auction.deposit)
            localDataSource.saveAuction(
                auction.copy(depositStatus = result.result)
            )
            result
        }
    }

    override suspend fun checkDepositPayResult() {
        runCatching {
            val result = remoteDataSource.checkDepositPayResult()
            localDataSource.updateDepositPayResult(result)
        }
    }
}
