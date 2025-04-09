package com.example.assaply.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.presentation.events.SearchEvent
import com.example.assaply.presentation.states.SearchState
import com.example.assaply.util.Constants.DEFAULT_SOURCES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                newsUsecases.searchNews(
                    query = _state.value.searchQuery,
                    sources = DEFAULT_SOURCES
                ).collect { pagingData ->

                    val articles = collectArticlesFromPagingData(pagingData)

                    _state.update {
                        it.copy(
                            articles = articles,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unexpected error"
                    )
                }
            }
        }
    }

    suspend fun collectArticlesFromPagingData(pagingData: PagingData<Article>): List<Article> {
        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.url == newItem.url
                override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
            },
            updateCallback = NoopListCallback(),
            mainDispatcher = Dispatchers.Main,
            workerDispatcher = Dispatchers.Default
        )

        differ.submitData(pagingData)
        delay(100)

        return differ.snapshot().items
    }

}


class NoopListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
