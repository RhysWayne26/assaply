package com.example.assaply.presentation.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.assaply.R

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null
) {
    var message by remember {
        mutableStateOf("No results found.")
    }
    var icon by remember {
        mutableStateOf(R.drawable.ic_network_error)
    }

    if (error != null) {
        message = parseErrorMessage(error)
        icon = R.drawable.ic_network_error
    }

    val startAnimation = remember { mutableStateOf(false) }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation.value) 0.3f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .alpha(alphaAnimation),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error.message) {
        null -> "Unknown error occurred."
        else -> error.error.message!!
    }
}
