package ru.me.searchmoviesapp.data.dto.full_cast

data class CastShortDto(
    val job: String,
    val items: List<CastShortItemDto>,
)