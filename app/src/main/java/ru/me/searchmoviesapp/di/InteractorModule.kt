package ru.me.searchmoviesapp.di

import org.koin.dsl.module
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.impl.MoviesInteractorImpl

val interactorModule = module {
    factory<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}