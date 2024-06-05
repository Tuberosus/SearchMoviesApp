package ru.me.searchmoviesapp.ui

sealed interface ScreenState {
    data object Loading: ScreenState
    data class Error(val message: String): ScreenState
    data class Content<T>(val content: T): ScreenState
}