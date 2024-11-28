package ru.me.searchmoviesapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.me.searchmoviesapp.BuildConfig
import ru.me.searchmoviesapp.data.dto.full_cast.FullCastResponse
import ru.me.searchmoviesapp.data.dto.movies.MoviesSearchResponse
import ru.me.searchmoviesapp.data.dto.names.NamesResponse
import ru.me.searchmoviesapp.domain.models.MovieDetails

interface IMDbApiService {
    companion object {
        private const val KEY = BuildConfig.TV_API_KEY
    }
    @GET("/en/API/SearchMovie/${KEY}/{expression}")
    suspend fun searchMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title/${KEY}/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetails

    @GET("/en/API/FullCast/${KEY}/{movie_id}")
    suspend fun getFullCast(@Path("movie_id") movieId: String): FullCastResponse

    @GET("/en/API/SearchName/${KEY}/{expression}")
    suspend fun getActors(@Path("expression") expression: String): NamesResponse
}