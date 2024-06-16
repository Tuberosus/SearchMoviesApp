package ru.me.searchmoviesapp.domain.impl

import java.util.concurrent.Executors
import ru.me.searchmoviesapp.domain.api.NamesRepository
import ru.me.searchmoviesapp.domain.api.SearchNamesUseCase
import util.Resource

class SearchNamesUseCaseImpl(private val repository: NamesRepository): SearchNamesUseCase {

    private val executor = Executors.newCachedThreadPool()
    override fun search(expression: String, consumer: SearchNamesUseCase.NamesConsumer) {
        executor.execute {
            when (val resource = repository.searchName(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
        }
    }
}