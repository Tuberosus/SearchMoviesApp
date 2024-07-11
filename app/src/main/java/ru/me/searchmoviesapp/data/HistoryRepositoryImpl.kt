package ru.me.searchmoviesapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.me.searchmoviesapp.data.converters.MovieDbConvertor
import ru.me.searchmoviesapp.data.db.AppDatabase
import ru.me.searchmoviesapp.data.db.entity.MovieEntity
import ru.me.searchmoviesapp.domain.db.HistoryRepository
import ru.me.searchmoviesapp.domain.models.Movie

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MovieDbConvertor,
): HistoryRepository {

    override fun historyMovies(): Flow<List<Movie>> = flow {
        val movies = appDatabase.movieDao().getMovies()
        emit(convertFromMovieEntity(movies))
    }

    private fun convertFromMovieEntity(movies: List<MovieEntity>): List<Movie> {
        return movies.map { movie -> movieDbConvertor.map(movie) }
    }
}