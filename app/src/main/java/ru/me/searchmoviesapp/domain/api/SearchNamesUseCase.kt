package ru.me.searchmoviesapp.domain.api

import ru.me.searchmoviesapp.domain.models.Name

interface SearchNamesUseCase {
    fun search(expression: String, consumer: NamesConsumer)
    fun interface NamesConsumer {
        fun consume(foundActors: List<Name>?, errorMessage: String?)
    }
}