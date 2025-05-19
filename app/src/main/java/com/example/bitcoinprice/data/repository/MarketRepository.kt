package com.example.bitcoinprice.data.repository

import android.util.Log
import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.local.entity.Valor
import com.example.bitcoinprice.data.model.MarketPriceResponse
import com.example.bitcoinprice.data.network.RetrofitClient
import com.example.bitcoinprice.ui.data.Coord
import com.example.bitcoinprice.ui.data.ScreenUIData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarketRepository(private val db: AppDatabase) {
    private val local = db.marketPriceDao()

    suspend fun getMarketPrices(range: String): ScreenUIData {
        val result = getData(range)

        return if (result.isSuccess) {
            val entity = result.getOrNull()
            if (entity != null) {
                ScreenUIData(
                    name = entity.name,
                    description = entity.description,
                    values = entity.valores.map { Coord(x = it.x, y = it.y) },
                    isLoading = false,
                    isError = false
                )
            } else {
                ScreenUIData(
                    name = "",
                    description = "",
                    values = emptyList(),
                    isLoading = false,
                    isError = false
                )
            }
        } else {
            // Erro de requisição ou exceção
            ScreenUIData(
                name = "",
                description = "",
                values = emptyList(),
                isLoading = false,
                isError = true
            )
        }
    }

    private suspend fun updateLocal(marketData: MarketPriceResponse?, range: String) {
        Log.d("MarketRepository.kt", "há dados no response $marketData")

        if (marketData == null || marketData.values.isEmpty()) return

        withContext(Dispatchers.IO) {
            val datadb = MarketPriceEntity(
                range = range,
                name = marketData.name.orEmpty(),
                description = marketData.description.orEmpty(),
                valores = marketData.values.map { Valor(x = it.x, y = it.y) }
            )
            local.insertAll(datadb)
        }
    }

    suspend fun getData(range: String): Result<MarketPriceEntity?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getMarketPrice(timespan = range)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.values.isNotEmpty()) {
                        updateLocal(body, range)
                    }
                    Result.success(local.getByRange(range))
                } else {
                    // Tenta retornar dados locais se o remoto falhar
                    Result.success(local.getByRange(range))
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Result.failure(ex)
            }
        }
    }
}