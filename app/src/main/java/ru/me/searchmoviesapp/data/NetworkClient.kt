package ru.me.searchmoviesapp.data

import ru.me.searchmoviesapp.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}