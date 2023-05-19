package com.tw.auction_demo.auctions.model

class AuctionDetailModel(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val startingPrice: Double,
    val currentPrice: Double,
    val bidCount: Int,
    val startTime: String,
    val endTime: String,
    val status: String,
    val seller: String,
    val bidTimes: Int,
    val deposit: String
)
