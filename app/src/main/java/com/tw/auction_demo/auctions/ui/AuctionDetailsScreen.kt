package com.tw.auction_demo.auctions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tw.auction_demo.auctions.model.AuctionModel

@Composable
fun AuctionDetailsScreen(viewModel: AuctionsViewModel) {
    val uiState by viewModel.auctionDetailsUIState.collectAsState()
    when (uiState) {
        is AuctionsViewModel.AuctionDetailsUIState.Loading -> {
        }

        is AuctionsViewModel.AuctionDetailsUIState.Success -> {
            AuctionContentScreen(
                auction = (uiState as AuctionsViewModel.AuctionDetailsUIState.Success).auction,
                viewModel = viewModel
            )
        }

        is AuctionsViewModel.AuctionDetailsUIState.Error -> {
            AuctionErrorScreen()
        }
    }
}

@Composable
fun AuctionContentScreen(
    auction: AuctionModel,
    viewModel: AuctionsViewModel
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(Modifier.wrapContentHeight()) {
            ImageScreen(auction.image)
            Text(
                text = auction.status,
                fontSize = 18.sp,
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = auction.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(12.dp))
            ItemDescriptionScreen(label = "简介", content = auction.description)
            Spacer(modifier = Modifier.width(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
            ) {
                ItemDescriptionScreen(label = "起拍价", content = auction.startingPrice.toString())
                Spacer(modifier = Modifier.width(12.dp))
                ItemDescriptionScreen(label = "保证金", content = auction.deposit)
                Spacer(modifier = Modifier.width(12.dp))

                ItemDescriptionScreen(label = "佣金", content = "8%~11%")

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
            ) {
                ItemDescriptionScreen(label = "加价幅度", content = "200")
                Spacer(modifier = Modifier.width(12.dp))
                ItemDescriptionScreen(label = "出价次数", content = auction.bidTimes.toString())
            }
            Spacer(modifier = Modifier.height(2.dp))
            ItemDescriptionScreen(label = "起拍时间", content = "2023-05-19 12:00:00")
            Spacer(modifier = Modifier.height(2.dp))
            ItemDescriptionScreen(label = "结束时间", content = "2023-05-23 12:00:00")
            Spacer(modifier = Modifier.height(12.dp))
            DepositStatusScreen(auction, viewModel = viewModel)
        }
    }
}

@Composable
fun ItemDescriptionScreen(label: String, content: String) {
    Row {
        Text(
            text = "$label: ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = content,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun DepositStatusScreen(
    auction: AuctionModel,
    viewModel: AuctionsViewModel
) {
    var depositStatusUIState: String by remember {
        mutableStateOf(auction.depositStatus)
    }
    val auctionState by viewModel.auctionDetailsUIState.collectAsState()

    LaunchedEffect(auctionState) {
        if (auctionState is AuctionsViewModel.AuctionDetailsUIState.Success) {
            depositStatusUIState =
                (auctionState as AuctionsViewModel.AuctionDetailsUIState.Success).auction.depositStatus
        }
    }

    when (depositStatusUIState) {
        "未支付" -> {
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth()
                    .padding(vertical = 2.dp, horizontal = 25.dp),
                onClick = { viewModel.depositsPay(auction) }
            ) {
                Text(
                    text = "支付保证金"
                )
            }
        }

        "已支付" -> {
            Text(
                text = "保证金支付成功，请参与竞拍"
            )
        }

        "支付失败" -> {
            Text(
                text = "保证金支付失败，请重试"
            )
        }

        else -> {
            Text(
                text = depositStatusUIState,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AuctionErrorScreen() {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "未能查询到拍品详情，有可能已经撤拍啦！",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
