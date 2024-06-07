package ru.me.searchmoviesapp.ui.movies_cast.view_model

import ru.me.searchmoviesapp.ui.movies_cast.MoviesCastRVItem

sealed interface MoviesCastState {
    data object Loading : MoviesCastState
    data class Error(val message: String) : MoviesCastState
    data class Content(
        val fullTitle: String,
        val items: List<MoviesCastRVItem>
    ) : MoviesCastState
}