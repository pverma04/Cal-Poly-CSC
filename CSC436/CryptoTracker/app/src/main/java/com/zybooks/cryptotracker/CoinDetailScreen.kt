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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.zybooks.cryptotracker.model.Coin
import java.text.NumberFormat
import java.util.Locale
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CoinDetailScreen(
    coin: Coin,
    onBack: () -> Unit
) {
    val changeColor =
        if (coin.priceChange24h >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = coin.imageUrl,
                contentDescription = coin.name,
                modifier = Modifier
                    .size(72.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = coin.name, fontWeight = FontWeight.Bold)
            Text(text = "Symbol: ${coin.symbol}")
            Text(text = "ID: ${coin.id}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Current price: ${formatUsd(coin.currentPrice)}",
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "24h change: ${String.format("%.2f", coin.priceChange24h)} %",
                color = changeColor,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "24h high: ${formatUsdNullable(coin.high24h)}")
            Text(text = "24h low: ${formatUsdNullable(coin.low24h)}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Market cap: $${formatBigNumber(coin.marketCap)}")
            Text(text = "Total volume: $${formatBigNumber(coin.totalVolume)}")
        }
    }
}

private fun formatUsd(value: Double): String {
    val nf = NumberFormat.getCurrencyInstance(Locale.US)
    nf.maximumFractionDigits = 2
    return nf.format(value)
}

private fun formatUsdNullable(value: Double?): String {
    return value?.let { formatUsd(it) } ?: "N/A"
}

private fun formatBigNumber(value: Double?): String {
    if (value == null) return "N/A"

    val abs = kotlin.math.abs(value)

    return when {
        abs >= 1_000_000_000_000 -> String.format("%.2fT", value / 1_000_000_000_000)
        abs >= 1_000_000_000     -> String.format("%.2fB", value / 1_000_000_000)
        abs >= 1_000_000         -> String.format("%.2fM", value / 1_000_000)
        abs >= 1_000             -> String.format("%.2fK", value / 1_000)
        else -> value.toString()
    }
}