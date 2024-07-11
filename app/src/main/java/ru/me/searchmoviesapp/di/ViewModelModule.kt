package ru.me.searchmoviesapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.me.searchmoviesapp.presentation.names.NamesViewModel
import ru.me.searchmoviesapp.presentation.details.InfoDetailsViewModel
import ru.me.searchmoviesapp.presentation.details.PosterDetailsViewModel
import ru.me.searchmoviesapp.presentation.history.HistoryViewModel
import ru.me.searchmoviesapp.presentation.movies_search.MoviesSearchViewModel
import ru.me.searchmoviesapp.presentation.movies_cast.MovieCastViewModel

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
    viewModel {
        NamesViewModel(get(), androidApplication())
    }
    viewModel {
        HistoryViewModel(androidContext(), get())
    }
}