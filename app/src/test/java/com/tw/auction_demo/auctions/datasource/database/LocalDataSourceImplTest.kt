package com.tw.auction_demo.auctions.datasource.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tw.auction_demo.auctions.datasource.database.dao.AuctionDatabase
import com.tw.auction_demo.utils.DB_AUCTIONS
import com.tw.auction_demo.utils.mockAuctionModel
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException


@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [30],
    instrumentedPackages = ["androidx.loader.content"],
)
@Suppress("DEPRECATION")
class LocalDataSourceImplTest {

    private lateinit var db: AuctionDatabase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AuctionDatabase::class.java).build()
        initData()
        localDataSource = LocalDataSourceImpl(auctionDao = db.auctionDao())

    }

    private fun initData() = runBlocking {
        db.auctionDao().insertAll(*DB_AUCTIONS.toTypedArray())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        unmockkAll()
    }

    @Test
    fun givenValidId_whenFindInLocal_thenAssertTheReturnDataWasValid() = runBlocking {
        val result = db.auctionDao().findAuction("1001")
        assertThat(mockAuctionModel, equalTo(result))
    }
}
