package ru.me.searchmoviesapp.domain.api

import kotlinx.coroutines.flow.Flow
import ru.me.searchmoviesapp.domain.models.Name

interface SearchNamesUseCase {
    fun search(expression: String): Flow<Pair<List<Name>?, String?>>
}