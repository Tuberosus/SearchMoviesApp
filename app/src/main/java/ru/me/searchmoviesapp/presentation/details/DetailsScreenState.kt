package ru.me.searchmoviesapp.presentation.details

import ru.me.searchmoviesapp.domain.models.MovieDetails

sealed interface DetailsScreenState {
    data object Loading: DetailsScreenState
    data class Error(val errorMessage: String): DetailsScreenState
    data class Content(val movieDetails: MovieDetails): DetailsScreenState
}