package ru.me.searchmoviesapp.domain.api

import ru.me.searchmoviesapp.domain.models.Movie
import util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}