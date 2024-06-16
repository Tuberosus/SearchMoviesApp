package ru.me.searchmoviesapp.data.dto.names

import ru.me.searchmoviesapp.data.dto.Response

data class NamesResponse(
    val errorMessage: String,
    val expression: String,
    val results: List<NamesDto>,
    val searchType: String
) : Response()