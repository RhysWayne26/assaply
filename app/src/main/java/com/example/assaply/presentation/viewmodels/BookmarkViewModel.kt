package com.example.assaply.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.presentation.states.BookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUsecases: NewsUsecases
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUsecases.getSavedArticles()
            .onEach {
                _state.value = _state.value.copy(articles = it)
            }.launchIn(viewModelScope)
    }
}