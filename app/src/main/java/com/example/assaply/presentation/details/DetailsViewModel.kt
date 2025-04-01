package com.example.assaply.presentation.details
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.data.domain.model.Article
import com.example.assaply.data.domain.usecases.news.NewsUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUsecases: NewsUsecases
) : ViewModel() {

    private val _sideEffect = mutableStateOf<String?>(null)
    val sideEffect: String? get() = _sideEffect.value

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUsecases.selectArticle(event.article.url)
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
