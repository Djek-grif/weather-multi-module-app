package com.djekgrif.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.djekgrif.weather.core.designsystem.theme.WeatherTheme
import com.djekgrif.weather.navigation.WeatherNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                WeatherNavHost()
            }
        }
    }
}