package ru.me.searchmoviesapp.presentation.names

import ru.me.searchmoviesapp.domain.models.Name

sealed interface NamesScreenState {
    data object Loading : NamesScreenState
    data class Error(val message: String) : NamesScreenState
    data class Empty(val message: String) : NamesScreenState
    data class Content(val names: List<Name>) : NamesScreenState
}