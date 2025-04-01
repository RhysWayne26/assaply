package com.example.assaply.presentation.common

import androidx.annotation.DrawableRes
import com.example.assaply.R

data class Page(val title: String, val description: String = "", @DrawableRes val image: Int)

val pages = listOf(
    Page("Welcome", "to Assaply", image = R.drawable.welcum),
    Page("Only relevant", "hi-tech and politics news. Enjoy!", R.drawable.nightcity)
)