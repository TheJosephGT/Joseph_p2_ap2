package com.example.joseph_p2_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.joseph_p2_ap2.ui.theme.Joseph_p2_ap2Theme
import com.example.joseph_p2_ap2.ui.theme.counter.CounterScreen
import com.example.joseph_p2_ap2.ui.theme.counter.CounterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Joseph_p2_ap2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: CounterViewModel =  hiltViewModel()
                    val counter by viewModel.counter.collectAsState(0)
                    CounterScreen(
                        counter = counter,
                        onIncrement = viewModel::increment
                    )
                }
            }
        }
    }
}