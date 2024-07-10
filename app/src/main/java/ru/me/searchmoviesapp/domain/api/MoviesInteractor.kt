package ru.me.searchmoviesapp.domain.api

import kotlinx.coroutines.flow.Flow
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.domain.models.Movie
import ru.me.searchmoviesapp.domain.models.MovieDetails

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>

    fun getMovieDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>

    fun getFullCast(movieId: String): Flow<Pair<FullCastData?, String?>>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}