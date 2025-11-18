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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoQTheme {
                var showOnboarding by remember { mutableStateOf(false) }  // Boolean
                var onboardingPage by remember { mutableIntStateOf(0) }   // Int

                if (!showOnboarding) {
                    // Show Opening Screen (Splash)
                    OpeningScreen()
                    LaunchedEffect(Unit) {
                        delay(3000) // wait 3 seconds
                        showOnboarding = true
                    }
                } else {
                    // Show Onboarding screens
                    when (onboardingPage) {
                        0 -> Onboarding1()
                        1 -> Onboarding2()
                        2 -> Onboarding3()
                    }

                    LaunchedEffect(onboardingPage) {
                        delay(3000) // show each onboarding page for 3 seconds
                        onboardingPage = (onboardingPage + 1) % 3 // loop back to first page
                    }
                }
            }
        }
    }
}
