package com.example.assaply.presentation.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.assaply.R
import com.example.assaply.ui.theme.AssaplyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBrowsingClick: () -> Unit,
    onBookMarkClick: () -> Unit
) {

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body),
        ),
        title = {},
        actions = {

            TextButton(onClick = onBookMarkClick) {
                Text(text = "Mark")
            }

            TextButton(onClick = onBrowsingClick) {
                Text(text = "Browse")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsTopBarPreview() {
    AssaplyTheme {
        DetailsTopBar(
            onBookMarkClick = {},
            onBrowsingClick = {}
        )
    }
}