package com.example.assaply

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.presentation.navigation.components.Route
import com.example.assaply.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination: String by mutableStateOf(Route.APP_START_NAV)
        private set

    init {
        userPreferences.readAppEntry().onEach { shouldStartFromHomeScreen ->
            startDestination = if (shouldStartFromHomeScreen) {
                Route.NEWS_NAV
            } else {
                Route.APP_START_NAV
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}
