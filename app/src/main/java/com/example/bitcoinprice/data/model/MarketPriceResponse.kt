package com.example.bitcoinprice.data.model

data class MarketPriceResponse(
    val name: String,
    val description: String,
    val values: List<DataPoint>
)

data class DataPoint(
    val x: Long, //timestamp
    val y: Float //price
)