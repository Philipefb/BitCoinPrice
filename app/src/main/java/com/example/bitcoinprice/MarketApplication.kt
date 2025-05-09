package com.example.bitcoinprice

import android.app.Application
import androidx.room.Room
import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.repository.MarketRepository

class App : Application() {
    lateinit var repository: MarketRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "market_db"
        ).build()

        repository = MarketRepository(db)
    }
}
