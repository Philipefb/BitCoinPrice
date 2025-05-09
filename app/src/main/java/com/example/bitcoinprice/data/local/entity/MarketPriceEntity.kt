package com.example.bitcoinprice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_price")
data class MarketPriceEntity(
    @PrimaryKey
    val range: String,
    val name: String,
    val description: String,
    val valores: List<Valor>,
)

data class Valor(
    val x: Long,
    val y: Float
)

