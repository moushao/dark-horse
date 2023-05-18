package com.tw.auction_demo.di

import com.tw.auction_demo.auctions.datasource.AuctionLocalDataSource
import com.tw.auction_demo.auctions.datasource.remote.AuctionRemoteDataSource
import com.tw.auction_demo.auctions.repository.AuctionsRepository
import com.tw.auction_demo.auctions.ui.AuctionsViewModel
import com.tw.auction_demo.net.AuctionApi
import com.tw.auction_demo.net.AuctionService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val auctionModule = module {
    single<AuctionApi> { AuctionService }
    single { AuctionRemoteDataSource(get()) }
    single { AuctionLocalDataSource() }
    factory { AuctionsRepository(get(), get()) }
    viewModel { AuctionsViewModel(get()) }
}