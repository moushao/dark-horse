package com.tw.auction_demo.auctions.datasource.remote

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel
import com.tw.auction_demo.net.AuctionApi

class RemoteDataSourceImpl(private val auctionApi: AuctionApi) : RemoteDataSource {

    override suspend fun getAuctions(): List<AuctionListModel> {
        val response = auctionApi.getAuctions()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    override suspend fun getAuctionDetails(auctionId: String): AuctionDetailModel {
        val response = auctionApi.getAuctionDetails(auctionId)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    override suspend fun depositsPay(
        auctionId: String,
        amount: String
    ): DepositsPayResultModel {
        val response = auctionApi.depositsPay(auctionId, amount)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    override suspend fun checkDepositPayResult(): List<DepositsPayResultModel> {
        try {
            val response = auctionApi.checkDepositPayResult()
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return listOf()
    }
}
