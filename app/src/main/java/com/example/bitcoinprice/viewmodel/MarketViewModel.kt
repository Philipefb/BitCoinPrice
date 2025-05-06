package com.example.bitcoinprice.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.repository.MarketRepository
import kotlinx.coroutines.launch


class MarketViewModelFactory(private val repository: MarketRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MarketViewModel(private val repository: MarketRepository) : ViewModel() {

    private val _marketPrice = mutableStateOf<List<MarketPriceEntity?>>(emptyList())
    val marketPrice: State<List<MarketPriceEntity?>> = _marketPrice

    fun loadMarketPrice(range: String){
        val mappedRange = when (range) {
            "3d" -> "3days"
            "4d" -> "4days"
            "7d" -> "7days"
            "1m" -> "4weeks"
            "2m" -> "8weeks"
            else -> "4weeks"
        }
        Log.d("MarketViewModel", "Chamando loadMarketPrice com: $mappedRange")
        viewModelScope.launch {
            try {
                val response = repository.getMarketPrices(mappedRange)
                Log.d("MarketViewModel", "Dados recebidos: ${response.size}")
                _marketPrice.value = response
            } catch (e: Exception) {
                Log.e("MarketViewModel", "Erro ao carregar preços ${e.message} " , e)
            }
        }

    }

    init {
        loadMarketPrice("1")
    }
}