package com.tw.auction_demo.auctions.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tw.auction_demo.auctions.model.AuctionDetailModel

@Dao
interface AuctionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg auctionEntity: AuctionDetailModel)

    @Delete
    suspend fun delete(auctionEntity: AuctionDetailModel)

    @Query("SELECT * FROM auction WHERE id == :auctionId")
    suspend fun findAuction(auctionId: String): AuctionDetailModel
}
