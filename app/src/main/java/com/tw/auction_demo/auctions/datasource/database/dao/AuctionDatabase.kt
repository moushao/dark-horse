package com.tw.auction_demo.auctions.datasource.database.dao

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.tw.auction_demo.auctions.model.AuctionDetailModel


@Database(entities = [AuctionDetailModel::class], version = 2)
abstract class AuctionDatabase : RoomDatabase() {
    abstract fun auctionDao(): AuctionDao
}


