package com.example.assaply.presentation.welcome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assaply.presentation.Dimensions.IndicatorSize
import com.example.assaply.ui.theme.Purple40

@Composable
fun PageIndicator(
    pageSize: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Purple40
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageSize) { page ->
            Box(
                Modifier
                    .size(IndicatorSize)
                    .clip(CircleShape)
                    .background(if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}
//optimized