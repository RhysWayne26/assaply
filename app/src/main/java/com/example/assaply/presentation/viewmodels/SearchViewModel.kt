package com.example.assaply.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.presentation.events.SearchEvent
import com.example.assaply.presentation.states.SearchState
import com.example.assaply.util.Constants.DEFAULT_SOURCES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUsecases: NewsUsecases
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _articles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articles: StateFlow<PagingData<Article>> = _articles.asStateFlow()

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
        viewModelScope.launch {
            newsUsecases.searchNews(
                query = _state.value.searchQuery,
                sources = DEFAULT_SOURCES
            ).cachedIn(viewModelScope)
                .collect {
                    _articles.value = it
                }
        }
    }
}
