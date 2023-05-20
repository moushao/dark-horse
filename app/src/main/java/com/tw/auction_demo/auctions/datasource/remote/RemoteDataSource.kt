package com.tw.auction_demo.auctions.datasource.remote

import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

interface RemoteDataSource {
    suspend fun getAuctions(): List<AuctionListModel>

    suspend fun getAuction(auctionId: String): AuctionModel

    suspend fun depositsPay(auctionId: String, amount: String): DepositsPayResultModel
    suspend fun checkDepositPayResult():List<DepositsPayResultModel>
}
