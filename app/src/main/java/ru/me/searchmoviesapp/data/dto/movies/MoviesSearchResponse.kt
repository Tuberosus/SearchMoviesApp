package ru.me.searchmoviesapp.data.dto.movies

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MovieDto>) : Response()