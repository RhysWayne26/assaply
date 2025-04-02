package com.example.assaply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.assaply.presentation.navigation.NavGraph
import com.example.assaply.ui.theme.AssaplyTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.splashCondition }
        }

        setContent {
            AssaplyTheme {
                val systemUiController = rememberSystemUiController()
                val isDark = isSystemInDarkTheme()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color(0xFF00FFB2),
                        darkIcons = !isDark
                    )
                }

                NavGraph(startDestination = viewModel.startDestination)
            }
        }
    }
}



