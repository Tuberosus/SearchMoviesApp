package ru.me.searchmoviesapp.domain.db

import kotlinx.coroutines.flow.Flow
import ru.me.searchmoviesapp.domain.models.Movie

interface HistoryRepository {
    fun historyMovies(): Flow<List<Movie>>
}