package com.example.assaply.presentation.common

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
    onBookmarkClick: () -> Unit,
    onBrowsingClick: () -> Unit,
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
            TextButton(onClick = onBrowsingClick) {
                Text(text = "Browse")
            }
            TextButton(onClick = onBookmarkClick) {
                Text(text = "Mark/Unmark")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsTopBarPreview() {
    AssaplyTheme {
        DetailsTopBar(
            onBookmarkClick = {},
            onBrowsingClick = {}
        )
    }
}