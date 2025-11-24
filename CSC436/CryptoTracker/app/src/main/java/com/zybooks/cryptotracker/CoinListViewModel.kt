package com.zybooks.cryptotracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.cryptotracker.data.CryptoApi
import com.zybooks.cryptotracker.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CoinListUiState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val errorMessage: String? = null
)

class CoinListViewModel : ViewModel() {

    var uiState by mutableStateOf(CoinListUiState())
        private set

    fun loadCoins() {
        //show loading
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                CryptoApi.fetchTopCoins()
            }

            uiState = if (result.isEmpty()) {
                uiState.copy(
                    isLoading = false,
                    coins = emptyList(),
                    errorMessage = "Failed to load data"
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    coins = result,
                    errorMessage = null
                )
            }
        }
    }
}