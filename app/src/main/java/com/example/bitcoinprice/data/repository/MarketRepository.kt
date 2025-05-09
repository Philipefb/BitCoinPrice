package com.example.bitcoinprice.data.repository


import android.util.Log
import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.local.entity.Valor
import com.example.bitcoinprice.data.network.RetrofitClient
import com.example.bitcoinprice.ui.data.Coord
import com.example.bitcoinprice.ui.data.ScreenUIData

class MarketRepository(private val db: AppDatabase) {
    private val dao = db.marketPriceDao()

    suspend fun getMarketPrices(range: String): ScreenUIData {
        val cachedPrices = dao.getByRange(range)

        if (cachedPrices != null) {
            Log.d("MarketRepository.kt", "há dados no banco pegando localmente")
            return ScreenUIData(
                name = cachedPrices.name,
                description = cachedPrices.description,
                values = cachedPrices.valores.map { Coord(x = it.x, y = it.y) },
                isLoading = false,
                isError = false
            )
        }

        val response = RetrofitClient.api.getMarketPrice(timespan = range)
        val datadb = MarketPriceEntity(
            range = range,
            description = response.description,
            name = response.name,
            valores = response.values.map { Valor(x = it.x, y = it.y) }
        )

        val screenData = ScreenUIData(
            name = response.name,
            description = response.description,
            values = response.values.map { Coord(x = it.x, y = it.y) },
            isLoading = false,
            isError = false
        )

        dao.insertAll(datadb)

        return screenData
    }
}