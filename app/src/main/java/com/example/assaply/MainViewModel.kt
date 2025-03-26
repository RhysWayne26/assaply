package com.example.assaply

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.data.domain.usecases.app_entry.AppEntryUsecases
import com.example.assaply.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUsecases: AppEntryUsecases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination: String by mutableStateOf(Route.AppStartNav.route)
        private set

    init {
        appEntryUsecases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            startDestination = if (shouldStartFromHomeScreen) {
                Route.NewsNav.route
            } else {
                Route.AppStartNav.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}
