package com.example.assaply.presentation.events

sealed class WelcomeEvent {
    object SaveAppEntry : WelcomeEvent()
    object Skip : WelcomeEvent()
    object Finish : WelcomeEvent()
}