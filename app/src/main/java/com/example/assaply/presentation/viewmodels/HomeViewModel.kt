package com.example.assaply.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.assaply.data.domain.NewsUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    newsUsecases: NewsUsecases
): ViewModel() {
    val news = newsUsecases.getNews(
        sources = listOf("abc-news", "bbc-news", "al-jazeera-english")
    ).cachedIn(viewModelScope)
}