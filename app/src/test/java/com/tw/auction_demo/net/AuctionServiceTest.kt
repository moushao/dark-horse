package com.tw.auction_demo.net

import com.tw.auction_demo.auctions.model.AuctionDetailModel
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
                    filePath = "auctions/default.json"
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
        val result = service.getAuctionDetails("1001")

        // Then assert auction details info
        assertTrue(result.isSuccessful)
        val auctionDetail = result.body()!!
        assertEquals("123456789", auctionDetail.id)
        assertEquals("iPhone 12 Pro", auctionDetail.name)
        assertEquals("The latest iPhone model with advanced features.", auctionDetail.description)
        assertEquals("https://example.com/images/iphone12pro.jpg", auctionDetail.image)
        assertEquals(10, auctionDetail.bidCount)
        assertEquals("2023-05-18", auctionDetail.startTime)
        assertEquals("2023-05-23", auctionDetail.endTime)
        assertEquals("active", auctionDetail.status)
        assertEquals("John Doe", auctionDetail.seller)
        assertEquals(18, auctionDetail.bidTimes)
        assertEquals("200", auctionDetail.deposit)

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
        val result = service.getAuctionDetails("1003")

        // Then assert auction details info
        assertTrue(result.isSuccessful)
    }
}

