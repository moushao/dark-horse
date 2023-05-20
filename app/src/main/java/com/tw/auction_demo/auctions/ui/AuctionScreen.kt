package com.tw.auction_demo.auctions.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tw.auction_demo.auctions.model.AuctionListModel
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuctionScreen(
    viewModel: AuctionsViewModel
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val uiState by viewModel.uIState.collectAsState()
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (uiState) {
                    is AuctionsViewModel.UIState.AuctionListUIState -> {
                        AuctionListScreen(viewModel)
                    }

                    else -> {
                        AuctionDetailsScreen(viewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun AuctionListItemScreen(item: AuctionListModel, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick.invoke(item.id)
            }

    ) {

        ImageScreen(item.image)
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp),
                ) {
                    Text(
                        text = "起拍价:",
                        fontSize = 14.sp,
                    )
                    Text(
                        text = item.price.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "出价次数:",
                        fontSize = 14.sp,
                    )
                    Text(
                        text = item.bidTimes.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "加价幅度:200",
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "佣金:8%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "拍卖时间：2023-05-19 - 2023-05-22",
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun AuctionListScreen(
    viewModel: AuctionsViewModel
) {

    val uIState by viewModel.auctionUIState.collectAsState()
    when (uIState) {
        is AuctionsViewModel.AuctionListUIState.Success -> {
            LazyColumn {
                itemsIndexed((uIState as AuctionsViewModel.AuctionListUIState.Success).actions) { _, item ->
                    AuctionListItemScreen(item = item, onClick = viewModel::getAuctionDetails)
                }
            }
        }

        else -> {}
    }
}
