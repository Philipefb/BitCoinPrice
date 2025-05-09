package com.example.bitcoinprice.data.local.database

import androidx.room.TypeConverter
import com.example.bitcoinprice.data.local.entity.Valor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromValorList(value: List<Valor>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toValorList(value: String): List<Valor> {
        val listType = object : TypeToken<List<Valor>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
