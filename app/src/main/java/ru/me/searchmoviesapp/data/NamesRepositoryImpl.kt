package ru.me.searchmoviesapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.me.searchmoviesapp.data.dto.names.NamesRequest
import ru.me.searchmoviesapp.data.dto.names.NamesResponse
import ru.me.searchmoviesapp.domain.api.NamesRepository
import ru.me.searchmoviesapp.domain.models.Name
import ru.me.searchmoviesapp.util.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient): NamesRepository {
    override fun searchName(expression: String): Flow<Resource<List<Name>>> = flow{
        val response = networkClient.doRequest(NamesRequest(expression))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> {
                with(response as NamesResponse) {
                    val data = results.map {
                        Name(
                            description = it.description,
                            id = it.id,
                            image = it.image,
                            resultType = it.resultType,
                            title = it.title
                        )
                    }
                    emit(Resource.Success(data))
                }
            }
            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }
}