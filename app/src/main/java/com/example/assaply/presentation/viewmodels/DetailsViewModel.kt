package com.example.assaply.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.presentation.events.DetailsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUsecases: NewsUsecases
) : ViewModel() {

    private val _sideEffect = mutableStateOf<String?>(null)

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUsecases.getArticle(event.article.url)
                    if (article == null) {
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                _sideEffect.value = null
            }
        }
    }

    private suspend fun upsertArticle(article: Article) {
        newsUsecases.upsertArticle(article)
        _sideEffect.value = "Article Saved"
    }

    private suspend fun deleteArticle(article: Article) {
        newsUsecases.deleteArticle(article)
        _sideEffect.value = "Article Deleted"
    }
}