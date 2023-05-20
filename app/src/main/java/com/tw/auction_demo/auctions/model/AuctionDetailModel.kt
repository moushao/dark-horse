package com.tw.auction_demo.auctions.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auction")
data class AuctionDetailModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "startingPrice") val startingPrice: Double,
    @ColumnInfo(name = "currentPrice") val currentPrice: Double,
    @ColumnInfo(name = "bidCount") val bidCount: Int,
    @ColumnInfo(name = "startTime") val startTime: String,
    @ColumnInfo(name = "endTime") val endTime: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "seller") val seller: String,
    @ColumnInfo(name = "bidTimes") val bidTimes: Int,
    @ColumnInfo(name = "deposit") val deposit: String,
    @ColumnInfo(name = "depositStatus") val depositStatus: String,
)
