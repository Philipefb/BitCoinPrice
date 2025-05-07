package com.example.bitcoinprice.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bitcoinprice.data.local.dao.MarketInfoDao
import com.example.bitcoinprice.data.local.dao.MarketPriceDao
import com.example.bitcoinprice.data.local.entity.MarketInfoEntity
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity

@Database(entities = [MarketPriceEntity::class, MarketInfoEntity::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marketPriceDao(): MarketPriceDao
    abstract fun marketInfoDao(): MarketInfoDao
}
