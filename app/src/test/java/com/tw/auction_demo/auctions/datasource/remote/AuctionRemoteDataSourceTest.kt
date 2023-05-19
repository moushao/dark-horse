package com.tw.auction_demo.auctions.datasource.remote

import android.R.id
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.net.AuctionApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class AuctionRemoteDataSourceTest {
    private lateinit var remoteDataSource: AuctionRemoteDataSource
    private val actionApi: AuctionApi = mockk(relaxed = true)

    @Before
    fun setUp() {
        remoteDataSource = AuctionRemoteDataSourceImpl(actionApi)
    }

    @Test
    fun givenResponseWithAuctionList_whenExecuteGetAuctions_thenAssertResult() = runBlocking {
        // Given Response auction list
        val auctionListModel = AuctionListModel(
            id = "5",
            name = "Designer Handbag",
            description = "Luxurious designer handbag",
            image = "https://example.com/images/handbag.jpg",
            startingPrice = 500.00,
            currentPrice = 600.00,
            bidCount = 6,
            startTime = "2023-05-18T12:30:00Z",
            endTime = "2023-05-19T18:30:00Z",
            status = "active"
        )


        coEvery { actionApi.getAuctions() } returns Response.success(
            listOf(
                auctionListModel,
                auctionListModel
            )
        )

        // When execute getAuctions
        val result = remoteDataSource.getAuctions()

        // Then assert result
        assertEquals(2, result.size)
        kotlin.test.assertTrue {
            result.all {
                it == auctionListModel
            }
        }
    }

    @Test(expected = Exception::class)
    fun givenResponseWithFailed_whenExecuteGetAuctions_thenAssertResult(): Unit = runBlocking {
        // Given Response exception
        coEvery { actionApi.getAuctions() } throws Exception()
        // Then throw exception
        remoteDataSource.getAuctions()
    }

}
