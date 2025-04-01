package com.example.assaply.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.assaply.data.domain.usecases.news.NewsUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseсases: NewsUsecases
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.update { it.copy(searchQuery = event.searchQuery) }
            }

            is SearchEvent.SearchNews -> {
                searchNews()
            }
        }
    }

    private fun searchNews() {
        val articlesFlow = newsUseсases.searchNews(
            searchQuery = _state.value.searchQuery,
            sources = listOf("abc-news", "bbc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope)

        _state.update { it.copy(articles = articlesFlow) }
    }
}
