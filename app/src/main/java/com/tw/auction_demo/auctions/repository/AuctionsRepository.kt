package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel

interface AuctionsRepository {
    suspend fun getAuctions(): Result<List<AuctionListModel>>

    suspend fun getAuctionDetails(auctionId: String): Result<AuctionModel>
    suspend fun depositsPay(auction: AuctionModel): Result<DepositsPayResultModel>
    suspend fun checkDepositPayResult(): Result<List<DepositsPayResultModel>>
}
