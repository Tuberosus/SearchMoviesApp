package ru.me.searchmoviesapp.ui.movies_cast.view_model

import ru.me.searchmoviesapp.domain.models.FullCastData

sealed interface CastScreenState {
    data object Loading: CastScreenState
    data class Error(val msg: String): CastScreenState
    data class Content(val cast: FullCastData): CastScreenState
}