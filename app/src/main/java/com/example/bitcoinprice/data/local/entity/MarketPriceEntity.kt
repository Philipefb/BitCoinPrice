package com.example.bitcoinprice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_price")
data class MarketPriceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val x: Long,
    val y: Float
)

