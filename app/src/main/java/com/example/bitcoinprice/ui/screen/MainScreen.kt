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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.bitcoinprice.viewmodel.MarketViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


import java.util.Date

@Composable
fun MainScreen(viewModel: MarketViewModel) {
    val data = viewModel.marketPrice
    val options = listOf("3d", "4d", "7d", "1m", "2m")
    var selected by remember { mutableStateOf("1m") }


    if (data.value.isError) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Houve um erro incomum",
                fontSize = 16.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val totalHeight = maxHeight

            Column(
                modifier = Modifier
                    .height(totalHeight * 0.7f)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(totalHeight * 0.1f)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        options.forEach { option ->
                            Button(
                                onClick = {
                                    selected = option
                                    viewModel.loadMarketPrice(option)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(option.uppercase())
                            }
                        }
                    }
                }

                Text(
                    "Preço do Bitcoin nas últimas 4 semanas",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(6.dp)
                )

                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(data.value.values) { item ->
                        Column(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .width(200.dp)
                        ) {
                            Text(
                                text = "Dia: ${Date(item.x * 1000)}",
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Preço: R$ %.2f".format(item.y),
                                fontSize = 14.sp
                            )
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

                data.let {
                    Text(
                        text = data.value.description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}