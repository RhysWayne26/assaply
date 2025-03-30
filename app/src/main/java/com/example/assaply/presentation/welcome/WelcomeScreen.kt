package com.example.assaply.presentation.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assaply.presentation.Dimensions.MediumPadding2
import com.example.assaply.presentation.welcome.components.WelcomePage
import kotlinx.coroutines.launch
import androidx.constraintlayout.compose.ConstraintLayout

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    event: (WelcomeEvent)->Unit
) {
    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    onClick = { event(WelcomeEvent.Skip) },
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
                    onClick = { event(WelcomeEvent.Finish) },
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
    WelcomeScreen(event = {})
}