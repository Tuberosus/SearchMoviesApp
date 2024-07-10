package ru.me.searchmoviesapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.me.searchmoviesapp.data.SharedPreferences.LocalStorage
import ru.me.searchmoviesapp.data.converters.MovieCastConverter
import ru.me.searchmoviesapp.data.dto.names.NamesRequest
import ru.me.searchmoviesapp.data.dto.names.NamesResponse
import ru.me.searchmoviesapp.data.dto.full_cast.FullCastRequest
import ru.me.searchmoviesapp.data.dto.full_cast.FullCastResponse
import ru.me.searchmoviesapp.data.dto.movie_details.MovieDetailsRequest
import ru.me.searchmoviesapp.data.dto.movies.MoviesSearchRequest
import ru.me.searchmoviesapp.data.dto.movies.MoviesSearchResponse
import ru.me.searchmoviesapp.domain.api.MoviesRepository
import ru.me.searchmoviesapp.domain.models.Name
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.domain.models.Movie
import ru.me.searchmoviesapp.domain.models.MovieDetails
import util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) :
    MoviesRepository {

    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                with(response as MoviesSearchResponse) {
                    val data = results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description,
                            inFavorite = stored.contains(it.id)
                        )
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    override fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> emit(Resource.Success((response as MovieDetails)))
            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun getFullCast(movieId: String): Flow<Resource<FullCastData>> = flow {
        val response = networkClient.doRequest(FullCastRequest(movieId))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> {
                emit(
                    Resource.Success(
                        movieCastConverter.convert(response as FullCastResponse)
                    )
                )
            }

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }
}