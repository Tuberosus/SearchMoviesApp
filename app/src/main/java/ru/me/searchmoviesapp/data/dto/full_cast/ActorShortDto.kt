package ru.me.searchmoviesapp.data.dto.full_cast

data class ActorShortDto(
    val id: String,
    val image: String,
    val name: String,
    val asCharacter: String = "",
)