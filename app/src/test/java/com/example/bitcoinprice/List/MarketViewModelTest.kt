package com.example.bitcoinprice.List

import com.example.bitcoinprice.data.repository.MarketRepository
import com.example.bitcoinprice.ui.data.Coord
import com.example.bitcoinprice.ui.data.ScreenUIData
import com.example.bitcoinprice.viewmodel.MarketViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@ExperimentalCoroutinesApi
class MarketViewModelTest {

    private val repository: MarketRepository = mock()

    private lateinit var viewModel: MarketViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher(TestCoroutineScheduler()))
        viewModel = MarketViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loadMarketPrice should update marketPrice with success response`() = runTest {
        // Arrange
        val mockResponse = ScreenUIData(
            name = "Bitcoin",
            description = "Market price",
            values = listOf(Coord(1, 10f), Coord(2, 12f)),
            isLoading = false,
            isError = false
        )

        whenever(repository.getMarketPrices("4weeks")).thenReturn(mockResponse)

        // Act
        viewModel.loadMarketPrice("1m")
        advanceUntilIdle()

        // Assert
        val result = viewModel.marketPrice.value
        assertEquals("Bitcoin", result.name)
        assertEquals("Market price", result.description)
        assertFalse(result.isError)
        assertFalse(result.isLoading)
        assertEquals(2, result.values.size)
    }

    @Test
    fun `loadMarketPrice should update marketPrice with error state on exception`() = runTest {
        // Arrange
        whenever(repository.getMarketPrices("4weeks")).thenThrow(RuntimeException("Network error"))

        // Act
        viewModel.loadMarketPrice("1m")
        advanceUntilIdle()

        // Assert
        val result = viewModel.marketPrice.value
        assertTrue(result.isError)
        assertFalse(result.isLoading)
        assertEquals("", result.name)
        assertTrue(result.values.isEmpty())
    }
}
