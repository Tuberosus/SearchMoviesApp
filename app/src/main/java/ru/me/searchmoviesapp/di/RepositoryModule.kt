package ru.me.searchmoviesapp.di

import org.koin.dsl.module
import ru.me.searchmoviesapp.data.MoviesRepositoryImpl
import ru.me.searchmoviesapp.data.NamesRepositoryImpl
import ru.me.searchmoviesapp.domain.api.MoviesRepository
import ru.me.searchmoviesapp.domain.api.NamesRepository

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get())
    }

    single<NamesRepository> {
        NamesRepositoryImpl(get())
    }
}