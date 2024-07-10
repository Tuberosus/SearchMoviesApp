package ru.me.searchmoviesapp.domain.api

import kotlinx.coroutines.flow.Flow
import ru.me.searchmoviesapp.domain.models.Name
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.domain.models.Movie
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>>
    fun getFullCast(movieId: String): Flow<Resource<FullCastData>>
}