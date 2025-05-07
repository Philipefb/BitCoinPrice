package com.example.bitcoinprice.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitcoinprice.data.local.entity.MarketInfoEntity

@Dao
interface MarketInfoDao {
    @Query("SELECT * FROM market_info LIMIT 1")
    suspend fun getInfo(): MarketInfoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(info: MarketInfoEntity)
}