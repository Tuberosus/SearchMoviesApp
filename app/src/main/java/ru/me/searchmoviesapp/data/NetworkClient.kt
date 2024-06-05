package ru.me.searchmoviesapp.data

import ru.me.searchmoviesapp.data.dto.movies.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}