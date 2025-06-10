package com.example.bitcoinprice.ui.screen

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.bitcoinprice.ui.data.Coord
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line


@Composable
fun BitcoinLineChart(dataPoints: List<Coord>) {
    println("Dados recebidos:")
    println(dataPoints)

    if (dataPoints.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Sem dados para exibir", color = Color.Gray)
        }
        return
    }

    val chartValues =  dataPoints.map { it.y.toDouble() }

    LineChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        data = listOf(
                Line(
                    label = "Preço do Bitcoin",
                    values = chartValues,
                    color = SolidColor(Color(0xFF000000)),
                    firstGradientFillColor = Color(0xFF000000).copy(alpha = 0.5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                )
            ),
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
    )
}