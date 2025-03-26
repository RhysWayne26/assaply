package com.example.assaply.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaply.data.domain.usecases.app_entry.AppEntryUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val appEntryUsecases: AppEntryUsecases
): ViewModel() {
    fun onEvent(event: WelcomeEvent){
        when(event){
            is WelcomeEvent.SaveAppEntry->{
                saveAppEntry()
            }
        }
    }
    private fun saveAppEntry(){
        viewModelScope.launch{
            appEntryUsecases.saveAppEntry()
        }
    }
}