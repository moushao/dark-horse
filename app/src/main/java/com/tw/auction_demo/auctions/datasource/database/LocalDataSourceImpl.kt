package com.tw.auction_demo.auctions.datasource.database

import com.tw.auction_demo.auctions.datasource.database.dao.AuctionDao
import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

class LocalDataSourceImpl(private val auctionDao: AuctionDao) : LocalDataSource {

    override suspend fun saveAuction(auctionEntity: AuctionModel) {
        auctionDao.insertAll(auctionEntity)
    }

    override suspend fun getAuction(auctionId: String) = auctionDao.findAuction(auctionId)

    override fun updateDepositPayResult(result: List<DepositsPayResultModel>) {
    }

}
