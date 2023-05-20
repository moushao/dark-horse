package com.tw.auction_demo.auctions.ui

import com.tw.auction_demo.auctions.repository.AuctionsRepository
import com.tw.auction_demo.utils.mockAuctionListModel
import com.tw.auction_demo.utils.mockAuctionModel
import com.tw.auction_demo.utils.mockDepositsPayResultModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuctionsViewModelTest {

    private val repository = mockk<AuctionsRepository>(relaxed = true)
    private lateinit var viewModel: AuctionsViewModel
    val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        coEvery { repository.getAuctions() } returns Result.success(listOf(mockAuctionListModel))
        viewModel = AuctionsViewModel(repository)
    }

    @After
    fun tearsDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `test getAuctions when return success response then should be AuctionListUIState#Success`() =
        runTest {
            // Given
            val expectData = listOf(mockAuctionListModel)
            coEvery { repository.getAuctions() } returns Result.success(expectData)

            // When
            viewModel.getAuctions()
            advanceUntilIdle()

            // Then
            val data = viewModel.auctionsUIState.value
            assertTrue(data is AuctionsViewModel.AuctionListUIState.Success)
            assertEquals(expectData, (data as AuctionsViewModel.AuctionListUIState.Success).actions)
        }

    @Test
    fun `test getAuctions when return failed response then should be AuctionListUIState#Error`() =
        runTest {
            // Given
            coEvery { repository.getAuctions() } returns Result.failure(Exception())

            // When
            viewModel.getAuctions()

            advanceUntilIdle()
            // Then
            val data = viewModel.auctionsUIState.value
            assertTrue(data is AuctionsViewModel.AuctionListUIState.Error)
        }

    @Test
    fun `test checkDepositPayResult when return success response then should be AuctionListUIState#Success`() =
        runTest {
            // Given
            coEvery { repository.checkDepositPayResult() } returns Result.success(
                listOf(
                    mockDepositsPayResultModel
                )
            )
            // When
            advanceUntilIdle()
            // Then
            coVerify(exactly = 1) { repository.checkDepositPayResult() }
        }


    @Test
    fun `test getAuctionDetails when return success response then should be AuctionDetailsUIState#Success`() =
        runTest {
            // Given
            coEvery { repository.getAuctionDetails(any()) } returns Result.success(mockAuctionModel)

            // When
            viewModel.getAuctionDetails("1001")
            advanceUntilIdle()

            // Then
            val data = viewModel.auctionDetailsUIState.value
            assertTrue(data is AuctionsViewModel.AuctionDetailsUIState.Success)
            assertEquals(
                mockAuctionModel,
                (data as AuctionsViewModel.AuctionDetailsUIState.Success).auction
            )
        }

    @Test
    fun `test getAuctionDetails when return failed response then should be AuctionDetailsUIState#Success`() =
        runTest {
            // Given
            coEvery { repository.getAuctionDetails(any()) } returns Result.failure(Exception())

            // When
            viewModel.getAuctionDetails("1001")
            advanceUntilIdle()

            // Then
            val data = viewModel.auctionDetailsUIState.value
            assertTrue(data is AuctionsViewModel.AuctionDetailsUIState.Error)
        }

    @Test
    fun `test depositsPay when return success response then should be AuctionDetailsUIState#Success and not retry call  `() =
        runTest {
            // Given
            coEvery { repository.depositsPay(any()) } returns Result.success(
                mockDepositsPayResultModel
            )

            // When
            viewModel.depositsPay(mockAuctionModel)
            advanceUntilIdle()

            advanceTimeBy(60000)
            // Then
            val data = viewModel.auctionDetailsUIState.value
            assertTrue(data is AuctionsViewModel.AuctionDetailsUIState.Success)
            assertEquals(
                "已支付",
                (data as AuctionsViewModel.AuctionDetailsUIState.Success).auction.depositStatus
            )
            coVerify(exactly = 1) { repository.depositsPay(any()) }
        }

    @Test
    fun `test depositsPay when return failed response then should be AuctionDetailsUIState#Success but have try 10 times`() =
        runTest {
            // Given
            coEvery { repository.depositsPay(any()) } returns Result.failure(Exception())

            // When
            viewModel.depositsPay(mockAuctionModel)
            advanceTimeBy(60000)
            advanceUntilIdle()

            // Then
            val data = viewModel.auctionDetailsUIState.value
            assertTrue(data is AuctionsViewModel.AuctionDetailsUIState.Success)
            assertEquals(
                "支付失败",
                (data as AuctionsViewModel.AuctionDetailsUIState.Success).auction.depositStatus
            )
            coVerify(exactly = 11) { repository.depositsPay(any()) }
        }


    @Test
    fun `test backToAuctionsList when UIState should be AuctionListUIState`() =
        runTest {
            // Given
            coEvery { repository.getAuctionDetails(any()) } returns Result.failure(Exception())

            // When
            viewModel.backToAuctionsList()
            advanceUntilIdle()

            // Then
            val data = viewModel.uIState.value
            assertTrue(data is AuctionsViewModel.UIState.AuctionListUIState)
        }


}
