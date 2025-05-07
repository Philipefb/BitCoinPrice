package com.example.bitcoinprice.data.repository


import android.util.Log
import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.local.entity.MarketInfoEntity
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.network.RetrofitClient
import com.example.bitcoinprice.ui.data.Coord
import com.example.bitcoinprice.ui.data.ScreenUIData

class MarketRepository(private val db: AppDatabase) {
    private val dao = db.marketPriceDao()
    private val daoInfo = db.marketInfoDao()

    suspend fun getMarketPrices(range: String): ScreenUIData {
        val cachedPrices = dao.getAll()
        val cachedInfo = daoInfo.getInfo()
        if (cachedPrices.isNotEmpty() && range != "8weeks") {
            Log.d("MarketRepository.kt", "há dados no banco pegando localmente")
            val screenData = ScreenUIData(
                name = cachedInfo.name,
                description = cachedInfo.description,
                values = cachedPrices.map { Coord(x = it.x, y = it.y) },
                isLoading = false,
                isError = false
            )
            return screenData
        }

        val response = RetrofitClient.api.getMarketPrice(range)
        val infodb = MarketInfoEntity(
            name = response.name,
            description = response.description,
        )
        val datadb = response.values.map {
            MarketPriceEntity(
                x = it.x,
                y = it.y
            )
        }

        val screenData = ScreenUIData(
            name = response.name,
            description = response.description,
            values = response.values.map { Coord(x = it.x, y = it.y) },
            isLoading = false,
            isError = false
        )

        daoInfo.insertInfo(infodb)
        if (range != "8weeks") {
            dao.insertAll(datadb)
        }
        return screenData
    }
}