package com.tw.auction_demo.auctions.datasource.database

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

interface LocalDataSource {

    suspend fun saveAuction(auctionEntity: AuctionDetailModel)

    suspend fun getAuction(auctionId: String): AuctionDetailModel
    fun updateDepositPayResult(result: List<DepositsPayResultModel>)
}
