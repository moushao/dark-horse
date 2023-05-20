package com.tw.auction_demo.auctions.datasource.database

import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

interface LocalDataSource {

    suspend fun saveAuction(auctionEntity: AuctionModel)

    suspend fun getAuction(auctionId: String): AuctionModel
    fun updateDepositPayResult(result: List<DepositsPayResultModel>)
}
