package com.example.assaply.presentation.common

import android.content.Context
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.assaply.R

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null
) {
    val context = LocalContext.current
    val message = error?.let { parseErrorMessage(it, context) }
        ?: stringResource(R.string.no_results)

    val startAnimation = remember { mutableStateOf(false) }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation.value) 0.3f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fadeAnim"
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

fun parseErrorMessage(error: LoadState.Error, context: Context): String {
    return when {
        error.error.message == null -> context.getString(R.string.unknown_error)
        error.error.message!!.contains("timeout", ignoreCase = true) ->
            context.getString(R.string.timeout_error)
        else -> error.error.message!!
    }
}
