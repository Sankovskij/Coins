package com.example.composeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import com.example.composeexample.ui.MyApp
import com.example.composeexample.ui.theme.CoinsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinsTheme {
                Surface(color = MaterialTheme.colors.onPrimary) {
                    MyApp()
                }
            }
        }
    }
}


