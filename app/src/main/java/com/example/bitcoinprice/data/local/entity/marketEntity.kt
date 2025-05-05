package com.example.bitcoinprice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_price")
data class MarketPriceEntity(
    @PrimaryKey val x: Long,
    val y: Float,
    val description: String
)
