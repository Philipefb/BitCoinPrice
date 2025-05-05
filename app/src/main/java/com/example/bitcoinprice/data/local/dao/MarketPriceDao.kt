package com.example.bitcoinprice.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity

@Dao
interface MarketPriceDao {
    @Query("SELECT * FROM market_price")
    suspend fun getAll(): List<MarketPriceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prices: List<MarketPriceEntity>)
}
