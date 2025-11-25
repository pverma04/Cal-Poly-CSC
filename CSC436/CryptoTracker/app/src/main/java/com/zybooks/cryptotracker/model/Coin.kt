package com.zybooks.cryptotracker.model

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val currentPrice: Double,
    val priceChange24h: Double,
    val high24h: Double? = null,
    val low24h: Double? = null,
    val marketCap: Double? = null,
    val totalVolume: Double? = null
)