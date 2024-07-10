package ru.me.searchmoviesapp.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.me.searchmoviesapp.domain.api.NamesRepository
import ru.me.searchmoviesapp.domain.api.SearchNamesUseCase
import ru.me.searchmoviesapp.domain.models.Name
import ru.me.searchmoviesapp.util.Resource

class SearchNamesUseCaseImpl(private val repository: NamesRepository): SearchNamesUseCase {

    override fun search(expression: String): Flow<Pair<List<Name>?, String?>> {
        return repository.searchName(expression).map { result ->
            when (result) {
                is Resource.Success -> { Pair(result.data, null) }
                is Resource.Error -> { Pair(null, result.message) }
            }
        }
    }
}