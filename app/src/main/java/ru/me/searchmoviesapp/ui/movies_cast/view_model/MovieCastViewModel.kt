package ru.me.searchmoviesapp.ui.movies_cast.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.ui.ScreenState
import ru.me.searchmoviesapp.ui.details.DetailsScreenState

class MovieCastViewModel(
    private val application: Application,
    private val moviesInteractor: MoviesInteractor,
    private val movieId: String
) : ViewModel() {

    private val movieCastLiveData = MutableLiveData<ScreenState>()
    fun observeMovieCast(): LiveData<ScreenState> = movieCastLiveData

    init {
        getMovieCast()
    }

    private fun getMovieCast() {
        if (movieId != null) {
            movieCastLiveData.postValue(ScreenState.Loading)

            moviesInteractor.getFullCast(
                movieId = movieId,
                consumer = object : MoviesInteractor.CastConsumer {
                    override fun consume(foundCast: FullCastData?, errorMessage: String?) {
                        when (foundCast){
                            null -> movieCastLiveData.postValue(ScreenState.Error(errorMessage ?: ""))
                            else -> movieCastLiveData.postValue(ScreenState.Content(foundCast))
                        }
                    }
                }
            )
        } else {
            movieCastLiveData.postValue(ScreenState.Error(application.getString(R.string.something_went_wrong)))
        }
    }
}