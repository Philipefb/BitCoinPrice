package com.example.bitcoinprice.viewmodel

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

    init {
        viewModelScope.launch {
            try {
                val response = repository.getMarketPrices()
                _marketPrice.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}