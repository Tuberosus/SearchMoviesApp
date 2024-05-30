package util

import android.content.Context
import ru.me.searchmoviesapp.data.MoviesRepositoryImpl
import ru.me.searchmoviesapp.data.SharedPreferences.LocalStorage
import ru.me.searchmoviesapp.data.network.RetrofitNetworkClient
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.api.MoviesRepository
import ru.me.searchmoviesapp.domain.impl.MoviesInteractorImpl
import ru.me.searchmoviesapp.ui.poster.PosterPresenter
import ru.me.searchmoviesapp.ui.poster.PosterView

object Creator {
    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }


    fun providePosterPresenter(
        posterView: PosterView,
        imageUrl: String
    ): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(
            RetrofitNetworkClient(context),
            LocalStorage(context.getSharedPreferences("local_storage", Context.MODE_PRIVATE)),
        )
    }
}