package com.example.bitcoinprice.data.network

import com.example.bitcoinprice.data.model.MarketPriceResponse
import retrofit2.http.GET

interface ApiService {
    @GET("charts/market-price?timespan=4weeks&format=json")
    suspend fun getMarketPrice(): MarketPriceResponse
}