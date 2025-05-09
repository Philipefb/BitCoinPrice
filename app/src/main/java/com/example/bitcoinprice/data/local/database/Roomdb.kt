package com.example.bitcoinprice.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bitcoinprice.data.local.dao.MarketPriceDao
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity

@Database(entities = [MarketPriceEntity::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marketPriceDao(): MarketPriceDao
}
