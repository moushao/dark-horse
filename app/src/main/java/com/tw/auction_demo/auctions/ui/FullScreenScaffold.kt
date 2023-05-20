package com.tw.auction_demo.auctions.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FullScreenScaffold(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = content
    )
}
