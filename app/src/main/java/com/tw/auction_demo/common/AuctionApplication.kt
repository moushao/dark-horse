package com.tw.auction_demo.common

import android.app.Application
import com.tw.auction_demo.di.auctionModule
import org.koin.core.context.startKoin

class AuctionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(auctionModule)
        }
    }
}

