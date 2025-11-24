package com.zybooks.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.zybooks.cryptotracker.model.Coin
import com.zybooks.cryptotracker.ui.theme.CryptoTrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoTrackerTheme {
                var selectedCoin by remember { mutableStateOf<Coin?>(null) }

                if (selectedCoin == null) {
                    CoinListScreen(
                        onCoinClick = { coin ->
                            selectedCoin = coin
                        }
                    )
                } else {
                    CoinDetailScreen(
                        coin = selectedCoin!!,
                        onBack = { selectedCoin = null }
                    )
                }
            }
        }
    }
}
