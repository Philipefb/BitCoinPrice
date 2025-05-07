package com.example.bitcoinprice.ui.data

data class ScreenUIData(
    val name: String = "",
    val description: String = "",
    val values: List<Coord> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

data class Coord(
    val x: Long,
    val y: Float
)