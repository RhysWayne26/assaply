package com.example.assaply.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.presentation.events.WelcomeEvent
import com.example.assaply.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userPreferences: UserPreferences
): ViewModel() {
    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.SaveAppEntry,
            is WelcomeEvent.Skip,
            is WelcomeEvent.Finish -> {
                saveAppEntry()
            }
        }
    }
    private fun saveAppEntry(){
        viewModelScope.launch {
            userPreferences.saveAppEntry()
        }
    }
}