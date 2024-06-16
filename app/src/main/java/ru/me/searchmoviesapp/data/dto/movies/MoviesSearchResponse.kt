package ru.me.searchmoviesapp.data.dto.movies

import ru.me.searchmoviesapp.data.dto.Response

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MovieDto>) : Response()