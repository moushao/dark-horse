package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

interface AuctionsRepository {
    suspend fun getAuctions(): Result<List<AuctionListModel>>

    suspend fun getAuctionDetails(auctionId: String): Result<AuctionDetailModel>
    suspend fun depositsPay(auction:AuctionDetailModel): Result<DepositsPayResultModel>
    suspend fun checkDepositPayResult()
}
