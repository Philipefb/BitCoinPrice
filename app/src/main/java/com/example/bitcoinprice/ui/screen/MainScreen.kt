package com.example.bitcoinprice.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bitcoinprice.viewmodel.MarketViewModel
import androidx.compose.ui.text.font.FontWeight
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart

import java.util.Date

@Composable
fun MainScreen(viewModel: MarketViewModel) {
    val data = viewModel.marketPrice.value
    println("teste")
    println(viewModel.marketPrice.value)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val totalHeight = maxHeight

        Column(modifier = Modifier
            .height(totalHeight * 0.7f)
            .padding(16.dp)) {
            Text("Preço do Bitcoin nas últimas 4 semanas",
                fontSize = 20.sp,
                modifier = Modifier.padding(12.dp))

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(data) { item ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .width(200.dp)
                    ) {
                        if (item != null) {
                            Text(
                                text = "Dia: ${Date(item.x * 1000)}",
                                fontSize = 14.sp
                            )
                        }
                        if (item != null) {
                            Text(
                                text = "Preço: R$ %.2f".format(item.y),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(totalHeight * 0.3f)
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("About", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            data[0].let {
                if (it != null) {
                    Text(
                        text = it.description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}