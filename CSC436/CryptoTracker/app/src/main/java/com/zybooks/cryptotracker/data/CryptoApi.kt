package com.zybooks.cryptotracker.data

import com.zybooks.cryptotracker.model.Coin
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

object CryptoApi {

    //pulls top 10 (USD) coins by market cap
    fun fetchTopCoins(): List<Coin> {
        val urlString =
            "https://api.coingecko.com/api/v3/coins/markets" +
                    "?vs_currency=usd&order=market_cap_desc&per_page=10&page=1" +
                    "&sparkline=false&price_change_percentage=24h"

        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 10_000
            connection.readTimeout = 10_000

            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                emptyList()
            } else {
                val response = connection.inputStream
                    .bufferedReader()
                    .use { it.readText() }

                parseCoinsJson(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            connection.disconnect()
        }
    }

    private fun parseCoinsJson(json: String): List<Coin> {
        val jsonArray = JSONArray(json)
        val coins = mutableListOf<Coin>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)

            val id = obj.getString("id")
            val symbol = obj.getString("symbol")
            val name = obj.getString("name")
            val image = obj.getString("image")
            val price = obj.getDouble("current_price")
            val change = if (obj.isNull("price_change_percentage_24h")) {
                0.0
            } else {
                obj.getDouble("price_change_percentage_24h")
            }
            val high24h = if (obj.isNull("high_24h")) null else obj.getDouble("high_24h")
            val low24h = if (obj.isNull("low_24h")) null else obj.getDouble("low_24h")
            val marketCap = if (obj.isNull("market_cap")) null else obj.getDouble("market_cap")
            val totalVolume = if (obj.isNull("total_volume")) null else obj.getDouble("total_volume")

            coins.add(
                Coin(
                    id = id,
                    symbol = symbol.uppercase(),
                    name = name,
                    imageUrl = image,
                    currentPrice = price,
                    priceChange24h = change,
                    high24h = high24h,
                    low24h = low24h,
                    marketCap = marketCap,
                    totalVolume = totalVolume
                )
            )
        }

        return coins
    }
}