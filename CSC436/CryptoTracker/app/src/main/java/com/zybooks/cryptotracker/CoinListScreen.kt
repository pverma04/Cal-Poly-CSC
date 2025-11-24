package com.zybooks.cryptotracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.cryptotracker.model.Coin
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TextButton
import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun CoinListScreen(
    vm: CoinListViewModel = viewModel(),
    onCoinClick: (Coin) -> Unit
) {
    val uiState = vm.uiState

    LaunchedEffect(Unit) {
        vm.loadCoins()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CryptoTracker") },
                actions = {
                    TextButton(
                        onClick = {vm.loadCoins()},
                        enabled = !uiState.isLoading
                    ) {
                        Text("Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage ?: "Unknown error",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.coins) { coin ->
                            CoinRow(
                                coin = coin,
                                onClick = { onCoinClick(coin) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoinRow(
    coin: Coin,
    onClick: () -> Unit
) {
    val changeColor =
        if (coin.priceChange24h >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            //coin icons
            AsyncImage(
                model = coin.imageUrl,
                contentDescription = coin.name,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 12.dp)
            )

            Column {
                Text(text = coin.name, fontWeight = FontWeight.Bold)
                Text(text = coin.symbol, style = MaterialTheme.typography.labelMedium)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${String.format("%.2f", coin.currentPrice)}",
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${String.format("%.2f", coin.priceChange24h)} %",
                color = changeColor
            )
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEEEEE))
    )
}
