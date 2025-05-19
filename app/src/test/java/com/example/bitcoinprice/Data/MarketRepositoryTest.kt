package com.example.bitcoinprice.Data

import com.example.bitcoinprice.data.local.dao.MarketPriceDao
import com.example.bitcoinprice.data.local.database.AppDatabase
import com.example.bitcoinprice.data.local.entity.MarketPriceEntity
import com.example.bitcoinprice.data.local.entity.Valor
import com.example.bitcoinprice.data.repository.MarketRepository
import com.example.bitcoinprice.ui.data.Coord
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.spy
import java.net.UnknownHostException
import kotlin.test.Test

class MarketRepositoryTest {

    private val local: AppDatabase = mock()
    private val marketDao: MarketPriceDao = mock()

    private val repository by lazy {
        whenever(local.marketPriceDao()).thenReturn(marketDao)
        MarketRepository(local)
    }

    @Test
    fun `Given no internet connection when getting price then return local data`() = runTest {

        val localEntity = MarketPriceEntity(
            range = "1m",
            name = "Bitcoin",
            description = "Offline data",
            valores = listOf(Valor(1, 30000f), Valor(2, 31000f))
        )
        whenever(marketDao.getByRange("1m")).thenReturn(localEntity)

        val result = repository.getMarketPrices("1m")

        assertEquals("Bitcoin", result.name)
        assertEquals("Offline data", result.description)
        assertEquals(2, result.values.size)
        assertTrue(result.values.contains(Coord(1, 30000f)))
        assertTrue(!result.isError)
        assertTrue(!result.isLoading)
    }

    @Test
    fun `Given remote data when getting price then return ScreenUIData from remote`() = runTest {
        // Arrange: simula MarketPriceEntity já atualizada no banco local
        val remoteEntity = MarketPriceEntity(
            range = "1m",
            name = "Bitcoin",
            description = "Remote data",
            valores = listOf(Valor(1, 30000f), Valor(2, 32000f))
        )
        whenever(marketDao.getByRange("1m")).thenReturn(remoteEntity)

        // Simula que os dados do servidor foram processados com sucesso
        // O metodo getData foi testado indiretamente via esse stub
        val repoSpy = spy(repository)
        doReturn(Result.success(remoteEntity)).whenever(repoSpy).getData("1m")

        // Act
        val result = repoSpy.getMarketPrices("1m")

        // Assert
        assertEquals("Bitcoin", result.name)
        assertEquals("Remote data", result.description)
        assertEquals(2, result.values.size)
        assertEquals(Coord(1, 30000f), result.values[0])
        assertEquals(Coord(2, 32000f), result.values[1])
        assertEquals(false, result.isError)
        assertEquals(false, result.isLoading)
    }

    @Test
    fun `Given null remote and null local when getting price then return empty ScreenUIData`() = runTest {
        // Arrange
        whenever(marketDao.getByRange("1m")).thenReturn(null)

        val repoSpy = spy(repository)
        doReturn(Result.success(null)).whenever(repoSpy).getData("1m")

        // Act
        val result = repoSpy.getMarketPrices("1m")

        // Assert
        assertEquals("", result.name)
        assertEquals("", result.description)
        assertTrue(result.values.isEmpty())
        assertFalse(result.isError)
        assertFalse(result.isLoading)
    }

    @Test
    fun `Given exception when getting price and no local data then return error ScreenUIData`() = runTest {
        // Arrange
        val repoSpy = spy(repository)
        whenever(repoSpy.getData("1m")).thenReturn(Result.failure(RuntimeException("Network error")))

        // Act
        val result = repoSpy.getMarketPrices("1m")

        // Assert
        assertTrue(result.isError)
        assertTrue(result.values.isEmpty())
        assertEquals("", result.name)
        assertEquals("", result.description)
        assertFalse(result.isLoading)
    }

}