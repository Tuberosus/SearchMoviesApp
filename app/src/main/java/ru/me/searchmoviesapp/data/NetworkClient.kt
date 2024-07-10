package ru.me.searchmoviesapp.data

import ru.me.searchmoviesapp.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}