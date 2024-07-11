package ru.me.searchmoviesapp.di

import org.koin.dsl.module
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.api.SearchNamesUseCase
import ru.me.searchmoviesapp.domain.db.HistoryInteractor
import ru.me.searchmoviesapp.domain.impl.HistoryInteractorImpl
import ru.me.searchmoviesapp.domain.impl.MoviesInteractorImpl
import ru.me.searchmoviesapp.domain.impl.SearchNamesUseCaseImpl

val interactorModule = module {
    factory<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

    factory<SearchNamesUseCase> {
        SearchNamesUseCaseImpl(get())
    }

    single<HistoryInteractor> {
        HistoryInteractorImpl(get())
    }
}