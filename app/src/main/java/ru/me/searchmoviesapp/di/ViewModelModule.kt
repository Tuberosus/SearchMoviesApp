package ru.me.searchmoviesapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.me.searchmoviesapp.ui.details.view_model.InfoDetailsViewModel
import ru.me.searchmoviesapp.ui.details.view_model.PosterDetailsViewModel
import ru.me.searchmoviesapp.ui.movies.MoviesSearchViewModel
import ru.me.searchmoviesapp.ui.movies_cast.view_model.MovieCastViewModel

val viewModelModule = module {
    viewModel {
        MoviesSearchViewModel(androidApplication(), get())
}
    viewModel {(movieId: String) ->
        InfoDetailsViewModel(androidApplication(), get(), movieId)
    }
    viewModel { (posterUrl: String) ->
        PosterDetailsViewModel(posterUrl)
    }
    viewModel { (movieId: String) ->
        MovieCastViewModel(androidApplication(), get(), movieId)
    }
}