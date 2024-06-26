package ru.me.searchmoviesapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.me.searchmoviesapp.data.NetworkClient
import ru.me.searchmoviesapp.data.dto.MovieDetailsRequest
import ru.me.searchmoviesapp.data.dto.MoviesSearchRequest
import ru.me.searchmoviesapp.data.dto.Response

class RetrofitNetworkClient(
    private val context: Context,
    private val imdbService: IMDbApiService,
    ) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
//        if (dto !is MoviesSearchRequest) {
//            return Response().apply { resultCode = 400 }
//        }

        when (dto) {
            is MoviesSearchRequest -> {
                val response = imdbService.searchMovies(dto.expression).execute()
                val body = response.body()
                return if (body != null) {
                    body.apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            }
            is MovieDetailsRequest -> {
                val response = imdbService.getMovieDetails(dto.movieId).execute()
                val body = response.body()
                return if (body != null) {
                    body.apply { resultCode = response.code() }
                } else Response().apply { resultCode = response.code() }
            }
            else -> { return Response().apply { resultCode = 400 } }
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