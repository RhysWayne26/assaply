package com.example.assaply.presentation.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.assaply.presentation.welcome.components.WelcomePage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen() {
    val pagerState = rememberPagerState { pages.size }
    val buttonState by remember { derivedStateOf { listOf("", "Next", "Back", "GetStarted").chunked(2)[pagerState.currentPage] } }
    Column(Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { WelcomePage(page = pages[it]) }
    }
}
//optimized