package ru.me.searchmoviesapp.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.data.dto.movies.MoviesSearchResponse
import ru.me.searchmoviesapp.data.dto.full_cast.FullCastResponse

interface IMDbApiService {
    companion object {
        private const val KEY = "k_zcuw1ytf"
    }
    @GET("/en/API/SearchMovie/${KEY}/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/${KEY}/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetails>

    @GET("/en/API/FullCast/${KEY}/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<FullCastResponse>
}