package com.example.assaply.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.assaply.R
import com.example.assaply.ui.theme.AssaplyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onOpenInBrowser: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.read_more)) },
                    onClick = {
                        expanded = false
                        onOpenInBrowser()
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.bookmark)) },
                    onClick = {
                        expanded = false
                        onBookmarkClick()
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsTopBarPreview() {
    AssaplyTheme {
        DetailsTopBar(
            onBookmarkClick = {},
            onOpenInBrowser = {}
        )
    }
}