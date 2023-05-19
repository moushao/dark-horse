package com.tw.auction_demo.auctions.model

class AuctionListModel(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val startingPrice: Double,
    val currentPrice: Double,
    val bidCount: Int,
    val startTime: String,
    val endTime: String,
    val status: String
)

enum class ActionStatus
