package ru.me.searchmoviesapp.presentation.history

import ru.me.searchmoviesapp.domain.models.Movie

sealed interface HistoryState {

    data object Loading : HistoryState

    data class Content(
        val movies: List<Movie>
    ) : HistoryState

    data class Empty(
        val message: String
    ) : HistoryState
}