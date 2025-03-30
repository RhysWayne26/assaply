package com.example.assaply.presentation.welcome

sealed class WelcomeEvent {
    object SaveAppEntry : WelcomeEvent()
    object Skip : WelcomeEvent()
    object Finish : WelcomeEvent()
}