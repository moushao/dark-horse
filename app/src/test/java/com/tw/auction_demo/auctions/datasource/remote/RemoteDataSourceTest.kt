package com.tw.auction_demo.auctions.datasource.remote

import com.tw.auction_demo.auctions.model.AuctionDetailModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.model.DepositsPayResultModel
import com.tw.auction_demo.net.AuctionApi
import com.tw.auction_demo.utils.mockAuctionDetailsModel
import com.tw.auction_demo.utils.mockAuctionListModel
import com.tw.auction_demo.utils.mockDepositsPayResultModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class RemoteDataSourceTest {
    private lateinit var remoteDataSource: RemoteDataSource
    private val actionApi: AuctionApi = mockk(relaxed = true)

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSourceImpl(actionApi)
    }

    @Test
    fun givenResponseWithAuctionList_whenExecuteGetAuctions_thenAssertResult() = runBlocking {
        // Given Response auction list
        coEvery { actionApi.getAuctions() } returns Response.success(
            listOf(
                mockAuctionListModel,
                mockAuctionListModel
            )
        )

        // When execute getAuctions
        val result = remoteDataSource.getAuctions()

        // Then assert result
        assertEquals(2, result.size)
        kotlin.test.assertTrue {
            result.all {
                it == mockAuctionListModel
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

    @Test
    fun givenResponseWithAuctionDetails_whenExecuteGetAuctionDetails_thenAssertResult() =
        runBlocking {
            // Given Response auction list
            coEvery { actionApi.getAuctionDetails("1001") } returns Response.success(
                mockAuctionDetailsModel
            )

            // When execute getAuctions
            val result = remoteDataSource.getAuctionDetails("1001")

            // Then assert result
            assertEquals(mockAuctionDetailsModel, result)
        }

    @Test(expected = Exception::class)
    fun givenResponseWithFailed_whenExecuteGetAuctionDetails_thenAssertResult(): Unit =
        runBlocking {
            // Given Response exception
            coEvery { actionApi.getAuctionDetails(any()) } throws Exception()
            // Then throw exception
            remoteDataSource.getAuctionDetails("")
        }

    @Test
    fun givenResponseWithDepositsPayResult_whenExecuteDepositsPay_thenAssertResult() =
        runBlocking {
            // Given Response auction list
            coEvery { actionApi.depositsPay("1001", "") } returns Response.success(
                mockDepositsPayResultModel
            )

            // When execute getAuctions
            val result = remoteDataSource.depositsPay("1001", "")

            // Then assert result
            assertEquals(mockDepositsPayResultModel, result)
        }

    @Test(expected = Exception::class)
    fun givenResponseWithFailed_whenExecuteDepositsPay_thenAssertResult(): Unit =
        runBlocking {
            // Given Response exception
            coEvery { actionApi.depositsPay(any(), any()) } throws Exception()
            // Then throw exception
            remoteDataSource.depositsPay("", "")
        }

    @Test
    fun givenResponseWithListDepositsPayResultModel_whenExecuteCheckDepositPayResult_thenAssertResult() =
        runBlocking {
            // Given Response depositsPayResultModel list
            val expectData = listOf(mockDepositsPayResultModel)
            coEvery { actionApi.checkDepositPayResult() } returns Response.success(
                expectData
            )

            // When execute getAuctions
            val result = remoteDataSource.checkDepositPayResult()
            // Then assert result
            assertEquals(expectData, result)
        }

    @Test
    fun givenResponseWithFailed_whenExecuteCheckDepositPayResult_thenAssertResult(): Unit =
        runBlocking {
            // Given Response exception
            coEvery { actionApi.checkDepositPayResult() } throws Exception()
            // When throw exception
            val result = remoteDataSource.checkDepositPayResult()
            // Then assert result
            assertEquals(0, result.size)
        }
}
