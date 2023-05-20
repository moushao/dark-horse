package com.tw.auction_demo.auctions.datasource.database.dao

import android.content.Context
import androidx.room.Room

class DataBaseProvider(context: Context) {
    private var _auctionDao: AuctionDao
    val auctionDao: AuctionDao
        get() = _auctionDao

    init {
        _auctionDao = Room.databaseBuilder(
            context.applicationContext,
            AuctionDatabase::class.java,
            "auction.db"
        ).build().auctionDao()
    }
}
