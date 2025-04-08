package com.example.assaply.presentation.states

data class SearchState(
    val searchQuery: String = "",
    val isSearching: Boolean = false
)