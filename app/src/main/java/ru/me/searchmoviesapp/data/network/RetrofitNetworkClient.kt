package ru.me.searchmoviesapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.me.searchmoviesapp.data.NetworkClient
import ru.me.searchmoviesapp.data.dto.Response
import ru.me.searchmoviesapp.data.dto.full_cast.FullCastRequest
import ru.me.searchmoviesapp.data.dto.movie_details.MovieDetailsRequest
import ru.me.searchmoviesapp.data.dto.movies.MoviesSearchRequest
import ru.me.searchmoviesapp.data.dto.names.NamesRequest

class RetrofitNetworkClient(
    private val context: Context,
    private val imdbService: IMDbApiService,
    ) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }

        when (dto) {
            is MoviesSearchRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.searchMovies(dto.expression)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is MovieDetailsRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getMovieDetails(dto.movieId)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is FullCastRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getFullCast(dto.movieId)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is NamesRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getActors(dto.expression)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            else -> {
                return Response().apply { resultCode = 400 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}