package ru.me.searchmoviesapp.data

import ru.me.searchmoviesapp.data.dto.names.NamesRequest
import ru.me.searchmoviesapp.data.dto.names.NamesResponse
import ru.me.searchmoviesapp.domain.api.NamesRepository
import ru.me.searchmoviesapp.domain.models.Name
import util.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient): NamesRepository {
    override fun searchName(expression: String): Resource<List<Name>> {
        val response = networkClient.doRequest(NamesRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                Resource.Success((response as NamesResponse).results.map {
                    Name(
                        description = it.description,
                        id = it.id,
                        image = it.image,
                        resultType = it.resultType,
                        title = it.title
                    )
                })
            }
            else -> Resource.Error("Ошибка сервера")
        }
    }
}