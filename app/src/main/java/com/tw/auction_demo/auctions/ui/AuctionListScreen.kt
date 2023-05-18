package com.tw.auction_demo.auctions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tw.auction_demo.ui.theme.AuctiondemoTheme
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun AuctionListScreen(
    name: String,
    modifier: Modifier = Modifier,
    viewModel: AuctionsViewModel = get()
) {
    val auctions by viewModel.auctionUIState.collectAsState()
    Box(modifier.fillMaxSize())
    {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (auctions) {
                is AuctionsViewModel.AuctionListUIState.Success -> {
                    repeat((auctions as AuctionsViewModel.AuctionListUIState.Success).actions.size) {
                        Button(
                            modifier = Modifier
                                .height(48.dp)
                                .wrapContentWidth()
                                .padding(vertical = 2.dp, horizontal = 15.dp),
                            onClick = viewModel::getAuctions
                        ) {
                            Text(
                                text = "Hello $name!",
                                modifier = modifier
                            )
                        }
                    }
                }

                else -> {

                }
            }
        }

        Button(
            modifier = Modifier
                .height(48.dp)
                .wrapContentWidth()
                .padding(vertical = 2.dp, horizontal = 15.dp),
            onClick = viewModel::getAuctions
        ) {
            Text(
                text = "Hello $name!",
                modifier = modifier
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AuctiondemoTheme {
        AuctionListScreen("Android")
    }
}