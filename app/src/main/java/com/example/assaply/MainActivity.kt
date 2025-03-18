package com.example.assaply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.assaply.presentation.welcome.OnboardingScreen
import com.example.assaply.ui.theme.AssaplyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent{
            AssaplyTheme {
                OnboardingScreen()
            }
        }
    }
}



