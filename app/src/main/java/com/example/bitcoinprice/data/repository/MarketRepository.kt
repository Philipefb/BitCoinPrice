package com.example.bitcoinprice.data.repository


import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.network.RetrofitClient
import kotlin.math.log

class MarketRepository(private val db: AppDatabase) {
    private val dao = db.marketPriceDao()

    suspend fun getMarketPrices(): List<MarketPriceEntity> {
        val cached = dao.getAll()
        if (cached.isNotEmpty()) {
            println("há dados no banco pegando localmente")
            return cached
        }

        val response = RetrofitClient.api.getMarketPrice()
        println("Banco está vazio pegando os dados da internet")
        val coord = response.values.map {
            MarketPriceEntity(
                x = it.x,
                y = it.y,
                description = response.description
            )
        }

        dao.insertAll(coord)
        return coord
    }
}