package ru.me.searchmoviesapp.di

import org.koin.dsl.module
import ru.me.searchmoviesapp.data.HistoryRepositoryImpl
import ru.me.searchmoviesapp.data.MoviesRepositoryImpl
import ru.me.searchmoviesapp.data.NamesRepositoryImpl
import ru.me.searchmoviesapp.data.converters.MovieDbConvertor
import ru.me.searchmoviesapp.domain.api.MoviesRepository
import ru.me.searchmoviesapp.domain.api.NamesRepository
import ru.me.searchmoviesapp.domain.db.HistoryRepository

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get(), get(),get())
    }

    single<NamesRepository> {
        NamesRepositoryImpl(get())
    }

    factory {
        MovieDbConvertor()
    }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }
}