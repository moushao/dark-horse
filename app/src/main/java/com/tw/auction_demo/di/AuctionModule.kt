package com.tw.auction_demo.di

import android.content.Context
import androidx.room.Room
import com.tw.auction_demo.auctions.datasource.database.LocalDataSource
import com.tw.auction_demo.auctions.datasource.database.LocalDataSourceImpl
import com.tw.auction_demo.auctions.datasource.database.dao.AuctionDao
import com.tw.auction_demo.auctions.datasource.database.dao.AuctionDatabase
import com.tw.auction_demo.auctions.datasource.remote.RemoteDataSource
import com.tw.auction_demo.auctions.datasource.remote.RemoteDataSourceImpl
import com.tw.auction_demo.auctions.repository.AuctionsRepository
import com.tw.auction_demo.auctions.repository.AuctionsRepositoryImpl
import com.tw.auction_demo.auctions.ui.AuctionsViewModel
import com.tw.auction_demo.net.AuctionApi
import com.tw.auction_demo.net.AuctionService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val auctionModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(auctionDao = provideAuctionDao(get())) }
    single<AuctionApi> { AuctionService }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    factory<AuctionsRepository> { AuctionsRepositoryImpl(get(), get()) }
    viewModel { AuctionsViewModel(get()) }
}

fun provideAuctionDao(context: Context): AuctionDao {
    return Room.databaseBuilder(
        context.applicationContext,
        AuctionDatabase::class.java,
        "auction.db"
    ).build().auctionDao()
}
