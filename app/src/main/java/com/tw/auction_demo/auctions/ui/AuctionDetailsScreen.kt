package com.tw.auction_demo.auctions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tw.auction_demo.auctions.model.AuctionDetailModel
import org.koin.androidx.compose.get

@Composable
fun AuctionDetailsScreen(viewModel: AuctionsViewModel) {
    val uiState by viewModel.auctionDetailsUIState.collectAsState()
    when (uiState) {
        is AuctionsViewModel.AuctionDetailsUIState.Loading -> {

        }

        is AuctionsViewModel.AuctionDetailsUIState.Success -> {
            AuctionContentScreen(auction = (uiState as AuctionsViewModel.AuctionDetailsUIState.Success).action)
        }

        is AuctionsViewModel.AuctionDetailsUIState.Error -> {

        }
    }
}

@Composable
fun AuctionContentScreen(auction: AuctionDetailModel) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        ImageScreen(auction.image)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(Color.White)
        ) {
            Text(
                text = auction.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "简介：" + auction.description,
                fontSize = 14.sp,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
            ) {
                Text(
                    text = "起拍价:" + auction.startingPrice.toString(),
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "保证金:" + auction.deposit,
                    fontSize = 14.sp,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
            ) {
                Text(
                    text = "拍卖状态：" + auction.status,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "出价次数:" + auction.bidTimes.toString(),
                    fontSize = 14.sp,
                )
            }

            DepositStatusScreen(auction)

        }
    }
}

@Composable
fun DepositStatusScreen(
    auction: AuctionDetailModel,
    viewModel: AuctionsViewModel = get()
) {
    val depositStatusUIState by viewModel.depositStatusUIState.collectAsState()
    var testContent by remember { mutableStateOf(auction.depositStatus) }
    LaunchedEffect(Unit) {
        testContent = depositStatusUIState
    }

    when (depositStatusUIState) {
        "未支付" -> {
            val viewModel: AuctionsViewModel = get()
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth()
                    .padding(vertical = 2.dp, horizontal = 15.dp),
                onClick = {
                    viewModel.depositsPay(auction)
                    testContent = "dfadsf"

                }
            ) {
                Text(
                    text = testContent
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
                text = depositStatusUIState
            )
        }
    }
}
