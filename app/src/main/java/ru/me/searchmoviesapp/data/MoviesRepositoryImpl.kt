package ru.me.searchmoviesapp.data

import ru.me.searchmoviesapp.data.SharedPreferences.LocalStorage
import ru.me.searchmoviesapp.data.dto.MovieDetailsRequest
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.data.dto.MoviesSearchRequest
import ru.me.searchmoviesapp.data.dto.MoviesSearchResponse
import ru.me.searchmoviesapp.domain.api.MoviesRepository
import ru.me.searchmoviesapp.domain.models.Movie
import util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient, private val localStorage: LocalStorage) :
    MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                val stored = localStorage.getSavedFavorites()
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description, inFavorite = stored.contains(it.id))})
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> Resource.Success((response as MovieDetails))
            else -> Resource.Error("Ошибка сервера")
        }
    }
}