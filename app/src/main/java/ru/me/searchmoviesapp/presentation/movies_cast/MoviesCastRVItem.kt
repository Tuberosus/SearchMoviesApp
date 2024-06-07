package ru.me.searchmoviesapp.presentation.movies_cast

import ru.me.searchmoviesapp.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem {
    data class HeaderItem(val headerText: String) : MoviesCastRVItem
    data class PersonItem(val data: MovieCastPerson) : MoviesCastRVItem
}