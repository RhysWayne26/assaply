package com.example.assaply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.assaply.data.domain.entities.Article

import com.example.assaply.data.domain.entities.Source
import com.example.assaply.data.room.NewsDao
import com.example.assaply.presentation.navigation.NavGraph
import com.example.assaply.ui.theme.AssaplyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dao: NewsDao
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch{
            dao.upsert(
                Article(
                    author = "John Doe",
                    content = "Here is some preview content for the article.",
                    description = "This is a sample article used for Compose preview.",
                    publishedAt = "2025-03-29T12:00:00Z",
                    source = Source(id = "1", name = "MockSource"),
                    title = "Preview Article Title",
                    url = "https://example.com",
                    urlToImage = "https://example.com/image.jpg"
                )
            )
        }
        installSplashScreen().apply{
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }
        setContent{
            AssaplyTheme {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()
                SideEffect{
                    systemController.setSystemBarsColor(
                        color = Color.Red,
                        darkIcons = !isSystemInDarkMode
                    )
                }
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)){
                    val startDestination = viewModel.startDestination
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}



