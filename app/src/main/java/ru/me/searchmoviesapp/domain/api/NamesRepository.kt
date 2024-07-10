package ru.me.searchmoviesapp.domain.api

import kotlinx.coroutines.flow.Flow
import ru.me.searchmoviesapp.domain.models.Name
import ru.me.searchmoviesapp.util.Resource

interface NamesRepository {
    fun searchName(expression: String): Flow<Resource<List<Name>>>
}