package com.tw.auction_demo.net

import com.tw.auction_demo.utils.getTestRetrofit
import com.tw.auction_demo.utils.mockResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AuctionServiceTest() {

    @Test
    fun givenRemoteServiceWithValidResponse_whenExecuteGetAuctions_thenAssertResult() =
        runBlocking {
            // Given
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                mockResponse(
                    classLoader = javaClass.classLoader as ClassLoader,
                    filePath = "auctions.json"
                )
            )

            // When
            val service = getTestRetrofit(mockWebServer.url("/auctions/"))
                .create(AuctionApi::class.java)
            val result = service.getAuctions()

            // Then
            assertTrue(result.isSuccessful)
            assertEquals(5, result.body()!!.size)
            mockWebServer.shutdown()
        }

    @Test(expected = Exception::class)
    fun givenRemoteServiceWithInValidResponse_whenExecuteGetAuctions_thenAssertResult() =
        runBlocking {
            // Given Error Response
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                mockResponse(
                    classLoader = javaClass.classLoader as ClassLoader,
                    filePath = "invalid/default.json"
                )
            )
            // When execute getAuctions
            val service = getTestRetrofit(mockWebServer.url("/auctions/"))
                .create(AuctionApi::class.java)

            // Then should be throw exception
            service.getAuctions()
            mockWebServer.shutdown()
        }

    @Test()
    fun givenRemoteServiceWithValidResponse_whenExecuteGetAuctionDetails_thenAssertResult() = runBlocking {
        // Given
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(
            mockResponse(
                classLoader = javaClass.classLoader as ClassLoader,
                filePath = "auction_1001.json"
            )
        )

        // When
        val service = getTestRetrofit(mockWebServer.url("/auction/1001/"))
            .create(AuctionApi::class.java)
        val result = service.getAuction("1001")

        // Then assert auction details info
        assertTrue(result.isSuccessful)
        val auction = result.body()!!
        assertEquals("1001", auction.id)
        assertEquals("马家窑文化彩陶漩涡菱形几何纹双系壶", auction.name)
        assertEquals(10, auction.bidCount)
        assertEquals("2023-05-18", auction.startTime)
        assertEquals("2023-05-23", auction.endTime)
        assertEquals("正在拍卖中", auction.status)
        assertEquals("John Doe", auction.seller)
        assertEquals(18, auction.bidTimes)
        assertEquals("200", auction.deposit)

        mockWebServer.shutdown()
    }

    @Test
    fun givenRemoteServiceWithInEmptyResponse_whenExecuteGetAuctionDetails_thenAssertResult() = runBlocking{
        // Given
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(
            mockResponse(
                classLoader = javaClass.classLoader as ClassLoader,
                filePath = "auction_1003.json"
            )
        )

        // When
        val service = getTestRetrofit(mockWebServer.url("/auction/1003/"))
            .create(AuctionApi::class.java)
        val result = service.getAuction("1003")

        // Then assert auction details info
        assertTrue(result.isSuccessful)
    }
}

