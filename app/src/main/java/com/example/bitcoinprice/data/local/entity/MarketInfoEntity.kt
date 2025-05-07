package com.example.bitcoinprice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_info")
data class MarketInfoEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val description: String
)
