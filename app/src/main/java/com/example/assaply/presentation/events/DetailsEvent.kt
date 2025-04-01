package com.example.assaply.presentation.events

import com.example.assaply.data.domain.entities.Article

sealed class DetailsEvent{
    data class UpsertDeleteArticle(val article : Article) : DetailsEvent()
    object RemoveSideEffect : DetailsEvent()
}