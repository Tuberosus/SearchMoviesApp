package ru.me.searchmoviesapp.di

import org.koin.dsl.module
import ru.me.searchmoviesapp.data.MoviesRepositoryImpl
import ru.me.searchmoviesapp.domain.api.MoviesRepository

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get(), get())
    }
}