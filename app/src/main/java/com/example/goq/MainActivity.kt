package com.example.goq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import com.example.goq.ui.theme.GoQTheme
import com.example.goq.ui.screens.Onboarding1
import com.example.goq.ui.screens.Onboarding2
import com.example.goq.ui.screens.Onboarding3
import com.example.goq.ui.screens.OpeningScreen
import com.example.goq.ui.screens.Queue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoQTheme {
                Queue()
            }
        }
    }
}
