package com.tw.auction_demo.auctions.repository

import com.tw.auction_demo.auctions.datasource.database.LocalDataSource
import com.tw.auction_demo.auctions.datasource.remote.RemoteDataSource
import com.tw.auction_demo.utils.mockAuctionModel
import com.tw.auction_demo.utils.mockAuctionListModel
import com.tw.auction_demo.utils.mockDepositsPayResultModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test


class AuctionsRepositoryImplTest {
    private val remoteDataSource: RemoteDataSource = mockk(relaxed = true)
    private val localDataSource: LocalDataSource = mockk(relaxed = true)
    private val repository: AuctionsRepository =
        AuctionsRepositoryImpl(remoteDataSource, localDataSource)

    @Test
    fun `getAuctions should return Result Success with auction list`() = runBlocking {
        // Given mock the remoteDataSource response
        val auctionList = listOf(mockAuctionListModel)
        coEvery { remoteDataSource.getAuctions() } returns auctionList

        // When call the repository method
        val result = repository.getAuctions()

        // Then verify the result
        assertTrue(result.isSuccess)
        assertEquals(auctionList, result.getOrNull()!!)
    }

    @Test
    fun `getAuctions should return Result failed then asser result waa null`() = runBlocking {
        // Given mock the remoteDataSource response
        coEvery { remoteDataSource.getAuctions() } throws Exception()

        // When call the repository method
        val result = repository.getAuctions()

        // Then verify the result
        assertTrue(result.isFailure)
        assertNull(result.getOrNull())
    }

    @Test
    fun `test getAuction details with successful remote data source and save to local success`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote success
            coEvery { remoteDataSource.getAuction(any()) } returns mockAuctionModel
            coEvery { localDataSource.saveAuction(any()) } just Runs
            // When
            val actualResult = repository.getAuctionDetails("1001")

            // Then
            assertTrue(actualResult.isSuccess)
            assertEquals(mockAuctionModel, actualResult.getOrNull()!!)
            coVerify(exactly = 1) { localDataSource.saveAuction(any()) }
            coVerify(exactly = 0) { localDataSource.getAuction("1001") }
        }

    @Test
    fun `when getAuction details with failed remote data source then should getAuctionDetails from local`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote exception
            coEvery { remoteDataSource.getAuction(any()) } throws Exception()
            coEvery { localDataSource.getAuction(any()) } returns mockAuctionModel
            // When
            val actualResult = repository.getAuctionDetails("1001")

            // Then
            assertTrue(actualResult.isSuccess)
            assertEquals(mockAuctionModel, actualResult.getOrNull()!!)
            coVerify(exactly = 0) { localDataSource.saveAuction(any()) }
            coVerify(exactly = 1) { localDataSource.getAuction("1001") }
        }

    @Test
    fun `when getAuction details with failed from remote and local then should get failed result`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote exception
            coEvery { remoteDataSource.getAuction(any()) } throws Exception()
            coEvery { localDataSource.getAuction(any()) } throws Exception()
            // When
            val actualResult = repository.getAuctionDetails("1001")

            // Then
            assertTrue(actualResult.isFailure)
            assertNull(actualResult.getOrNull())
            coVerify(exactly = 0) { localDataSource.saveAuction(any()) }
            coVerify(exactly = 1) { localDataSource.getAuction("1001") }
        }

    @Test
    fun `when do depositsPay with remote successful data then should update auction depositStatus to local`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote exception
            coEvery {
                remoteDataSource.depositsPay(
                    any(),
                    any()
                )
            } returns mockDepositsPayResultModel
            coEvery { localDataSource.saveAuction(any()) } just Runs
            // When
            val actualResult = repository.depositsPay(mockAuctionModel)

            // Then
            assertTrue(actualResult.isSuccess)
            assertEquals(mockDepositsPayResultModel, actualResult.getOrNull()!!)
            coVerify(exactly = 1) { localDataSource.saveAuction(any()) }
        }

    @Test
    fun `when do depositsPay with remote failed data then should not update auction depositStatus to local`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote exception
            coEvery { remoteDataSource.depositsPay(any(), any()) } throws Exception()
            // When
            val actualResult = repository.depositsPay(mockAuctionModel)

            // Then
            assertTrue(actualResult.isFailure)
            coVerify(exactly = 0) { localDataSource.saveAuction(any()) }
        }

    @Test
    fun `when checkDepositPayResult with remote successful data then should update auction depositStatus to local`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote exception
            coEvery {
                remoteDataSource.depositsPay(
                    any(),
                    any()
                )
            } returns mockDepositsPayResultModel
            coEvery { localDataSource.saveAuction(any()) } just Runs
            // When
            val actualResult = repository.depositsPay(mockAuctionModel)

            // Then
            assertTrue(actualResult.isSuccess)
            assertEquals(mockDepositsPayResultModel, actualResult.getOrNull()!!)
            coVerify(exactly = 1) { localDataSource.saveAuction(any()) }
        }

    @Test
    fun `when checkDepositPayResult with remote failed data then should not update auctions depositStatus to local`() =
        runBlocking {
            // Given Mock mockAuctionDetailsModel when getAuction from remote exception
            coEvery { remoteDataSource.checkDepositPayResult() } throws Exception()

            // When
            val actualResult = repository.checkDepositPayResult()

            // Then
            assertTrue(actualResult.isFailure)
            coVerify(exactly = 0) { localDataSource.updateDepositPayResult(any()) }
        }

}
