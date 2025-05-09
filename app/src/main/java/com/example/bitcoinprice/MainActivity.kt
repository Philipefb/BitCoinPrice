package com.example.bitcoinprice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.bitcoinprice.data.repository.MarketRepository
import com.example.bitcoinprice.ui.screen.MainScreen
import com.example.bitcoinprice.ui.theme.BitCoinPriceTheme
import com.example.bitcoinprice.viewmodel.MarketViewModelFactory
import com.example.bitcoinprice.viewmodel.MarketViewModel
import com.example.bitcoinprice.data.local.database.AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as App
        val factory = MarketViewModelFactory(app.repository)
        val viewModel = ViewModelProvider(this, factory)[MarketViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            BitCoinPriceTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}