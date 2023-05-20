package com.tw.auction_demo.auctions.datasource.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tw.auction_demo.auctions.model.AuctionModel


@Database(entities = [AuctionModel::class], version = 2)
abstract class AuctionDatabase : RoomDatabase() {
    abstract fun auctionDao(): AuctionDao
}


