package com.example.bitcoinprice.data.repository


import android.util.Log
import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.network.RetrofitClient

class MarketRepository(private val db: AppDatabase) {
    private val dao = db.marketPriceDao()

    suspend fun getMarketPrices(range: String): List<MarketPriceEntity> {
        val cached = dao.getAll()
        if (cached.isNotEmpty() && range != "8weeks") {
            Log.d("MarketRepository.kt", "há dados no banco pegando localmente")
            return cached
        }

        val response = RetrofitClient.api.getMarketPrice(range)
        val coord = response.values.map {
            MarketPriceEntity(
                x = it.x,
                y = it.y,
                description = response.description
            )
        }

        if (range != "8weeks") {
            dao.insertAll(coord)
        }
        return coord
    }
}