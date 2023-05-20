package com.tw.auction_demo.auctions.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.tw.auction_demo.auctions.ui.theme.AuctiondemoTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuctionDetailsActivity : ComponentActivity() {
    val viewModel: AuctionsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuctiondemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuctionScreen(viewModel)
                }
            }
        }
    }

    override fun onBackPressed() {
        viewModel.backToAuctionsList()
    }
}
