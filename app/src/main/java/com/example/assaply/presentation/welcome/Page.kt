package com.example.assaply.presentation.welcome

import androidx.annotation.DrawableRes
import com.example.assaply.R

data class Page(val title: String, val description: String = "", @DrawableRes val image: Int)

val pages = listOf(
    Page("Welcome", image = R.drawable.welcum),
    Page("Only the newest news right here.", "Enjoy!", R.drawable.nightcity)
)
//optimized