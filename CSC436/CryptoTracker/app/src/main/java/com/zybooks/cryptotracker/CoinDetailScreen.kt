package com.zybooks.cryptotracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zybooks.cryptotracker.model.Coin

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CoinDetailScreen(
    coin: Coin,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(coin.name) },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("< Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = coin.name, fontWeight = FontWeight.Bold)
            Text(text = "Symbol: ${coin.symbol}")
            Text(text = "Current price: $${String.format("%.2f", coin.currentPrice)}")
            Text(text = "24h change: ${String.format("%.2f", coin.priceChange24h)} %")
            Text(text = "ID: ${coin.id}")
        }
    }
}