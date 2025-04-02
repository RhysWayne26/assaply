package com.example.assaply.presentation.navigation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assaply.R
import com.example.assaply.ui.theme.AssaplyTheme
import com.example.assaply.util.Dimensions.ExtraSmallPadding2
import com.example.assaply.util.Dimensions.IconSize

@Composable
fun NewsBottomNavigation(
    items: List<ButtonNavigationItem>,
    selected: Int,
    onItemClick:(Int)->Unit
){
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ){
        items.forEachIndexed{ index, item ->
            NavigationBarItem (
                selected = index == selected,
                onClick = {onItemClick(index)},
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize)
                        )
                        if (item.text.isNotBlank()) {
                            Spacer(modifier = Modifier.height(ExtraSmallPadding2))
                            Text(
                                text = item.text,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.body),
                    unselectedTextColor = colorResource(id = R.color.body),
                    indicatorColor = Color.Transparent
                )
            )

        }
    }
}

data class ButtonNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)


@Preview(showBackground = true)
@Composable
fun NewsBottomNavigationPreview() {
    AssaplyTheme {
        NewsBottomNavigation(
            items = listOf(
                ButtonNavigationItem(icon = R.drawable.ic_home, text = ""),
                ButtonNavigationItem(icon = R.drawable.ic_search, text = ""),
                ButtonNavigationItem(icon = R.drawable.ic_bookmark, text = "")
            ),
            selected = 0,
            onItemClick = {}
        )
    }
}