package ru.me.searchmoviesapp.ui.movies_cast.activity

import ru.me.searchmoviesapp.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem {
    data class HeaderItem(val headerText: String) : MoviesCastRVItem
    data class PersonItem(val data: MovieCastPerson) : MoviesCastRVItem
}