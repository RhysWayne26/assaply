package com.example.assaply.presentation.common

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assaply.R
import com.example.assaply.util.Dimensions.ArticleCardSize
import com.example.assaply.util.Dimensions.ExtraSmallPadding
import com.example.assaply.util.Dimensions.ExtraSmallPadding2
import com.example.assaply.util.Dimensions.SmallIconSize
import com.example.assaply.ui.theme.AssaplyTheme

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.shimmerEffect() = composed{
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue=0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis=1000),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    background(color = colorResource(id = R.color.shimmer).copy(alpha=alpha))
}

@Composable
fun ArticleCardShimmerEffect(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(ExtraSmallPadding)
    ) {
        val imageModifier = Modifier
            .size(ArticleCardSize)
            .clip(MaterialTheme.shapes.medium)
            .shimmerEffect()

        Box(modifier = imageModifier)

        Spacer(modifier = Modifier.width(ExtraSmallPadding))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .height(ArticleCardSize)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .height(MaterialTheme.typography.bodyMedium.lineHeight.value.dp)
                    .fillMaxWidth(0.9f)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(ExtraSmallPadding2))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(MaterialTheme.typography.labelMedium.lineHeight.value.dp)
                        .width(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                Box(
                    modifier = Modifier
                        .size(SmallIconSize)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                Box(
                    modifier = Modifier
                        .height(MaterialTheme.typography.labelMedium.lineHeight.value.dp)
                        .width(30.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview
@Composable
fun CardShimmerEffectPreview(){
    AssaplyTheme{
        ArticleCardShimmerEffect()
    }
}