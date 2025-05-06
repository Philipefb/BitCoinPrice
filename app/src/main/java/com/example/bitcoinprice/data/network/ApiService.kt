package com.example.bitcoinprice.data.network

import com.example.bitcoinprice.data.model.MarketPriceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("charts/market-price")
    suspend fun getMarketPrice(
        @Query("timespan") timespan: String = "4weeks",
        @Query("format") format: String = "json"
    ): MarketPriceResponse
}