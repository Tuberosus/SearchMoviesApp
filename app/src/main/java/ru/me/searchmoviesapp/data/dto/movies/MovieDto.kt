package ru.me.searchmoviesapp.data.dto.movies

data class MovieDto(val id: String,
                    val resultType: String,
                    val image: String,
                    val title: String,
                    val description: String)