package ru.me.searchmoviesapp.domain.api

import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    fun getMovieDetails(movieId: String, consumer: DetailsConsumer)
    interface DetailsConsumer {
        fun consume(foundDetails: MovieDetails?, errorMessage: String?)
    }

    fun getFullCast(movieId: String, consumer: CastConsumer)
    interface CastConsumer {
        fun consume(foundCast: FullCastData?, errorMessage: String?)
    }
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}