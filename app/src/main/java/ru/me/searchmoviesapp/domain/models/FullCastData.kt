package ru.me.searchmoviesapp.domain.models

import ru.me.searchmoviesapp.data.dto.full_cast.ActorShortDto
import ru.me.searchmoviesapp.data.dto.full_cast.CastShortDto

data class FullCastData(
    val imDbId: String,
    val title: String,
    val directors: List<MovieCastPerson>,
    val writers: List<MovieCastPerson>,
    val actors: List<MovieCastPerson>,
    val others: List<MovieCastPerson>,
)

data class MovieCastPerson(
    val id: String,
    val name: String,
    val description: String,
    val image: String?,
)