package com.tw.auction_demo.auctions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tw.auction_demo.R

@Composable
fun ImageScreen(url: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        AsyncImage(
            placeholder = if (LocalInspectionMode.current) {
                painterResource(R.drawable.ic_launcher_background)
            } else {
                null
            },
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = url,
            contentDescription = null,
        )
    }
}
