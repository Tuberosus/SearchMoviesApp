package ru.me.searchmoviesapp.presentation.movies_cast

sealed interface MoviesCastState {
    data object Loading : MoviesCastState
    data class Error(val message: String) : MoviesCastState
    data class Content(
        val fullTitle: String,
        val items: List<MoviesCastRVItem>
    ) : MoviesCastState
}