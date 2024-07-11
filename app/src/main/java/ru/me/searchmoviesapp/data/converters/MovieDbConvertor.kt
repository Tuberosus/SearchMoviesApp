package ru.me.searchmoviesapp.data.converters

import ru.me.searchmoviesapp.data.db.entity.MovieEntity
import ru.me.searchmoviesapp.data.dto.movies.MovieDto
import ru.me.searchmoviesapp.domain.models.Movie

class MovieDbConvertor {

    fun map(movie: MovieDto): MovieEntity {
        return MovieEntity(movie.id, movie.resultType, movie.image, movie.title, movie.description)
    }

    fun map(movie: MovieEntity): Movie {
        return Movie(movie.id, movie.resultType, movie.image, movie.title, movie.description)
    }
}