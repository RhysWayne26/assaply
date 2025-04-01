package com.example.assaply.presentation.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.assaply.presentation.Dimensions.MediumPadding2
import com.example.assaply.presentation.navgraph.Route
import com.example.assaply.presentation.welcome.components.WelcomePage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    event: (WelcomeEvent) -> Unit
) {
    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(state = pagerState) { page ->
            WelcomePage(page = pages[page])
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumPadding2)
        ) {
            val (skipButton, nextButton, finishButton) = createRefs()

            if (pagerState.currentPage < pages.lastIndex) {
                TextButton(
                    onClick = {
                        event(WelcomeEvent.Skip)
                        navController.navigate(Route.NewsNavScreen.route) {
                            popUpTo(Route.AppStartNav.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.constrainAs(skipButton) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                ) {
                    Text(text = "Skip")
                }

                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier.constrainAs(nextButton) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                ) {
                    Text(text = "Next")
                }
            } else {
                TextButton(
                    onClick = {
                        event(WelcomeEvent.Finish)
                        navController.navigate(Route.NewsNavScreen.route) {
                            popUpTo(Route.AppStartNav.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.constrainAs(finishButton) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
                ) {
                    Text(text = "Finish")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val fakeNavController = rememberNavController()
    WelcomeScreen(
        navController = fakeNavController,
        event = {}
    )
}
