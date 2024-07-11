package ru.me.searchmoviesapp.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.me.searchmoviesapp.domain.db.HistoryInteractor
import ru.me.searchmoviesapp.domain.db.HistoryRepository
import ru.me.searchmoviesapp.domain.models.Movie

class HistoryInteractorImpl(private val historyRepository: HistoryRepository) : HistoryInteractor {
    override fun historyMovies(): Flow<List<Movie>> {
        return historyRepository.historyMovies()
    }
}