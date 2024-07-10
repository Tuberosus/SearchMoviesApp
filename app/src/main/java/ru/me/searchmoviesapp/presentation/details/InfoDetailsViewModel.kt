package ru.me.searchmoviesapp.presentation.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.presentation.details.DetailsScreenState

class InfoDetailsViewModel(
    private val application: Application,
    private val moviesInteractor: MoviesInteractor,
    private val movieId: String?,
) : ViewModel() {


    private val infoDetailsState = MutableLiveData<DetailsScreenState>()

    fun getInfoDetailsState(): LiveData<DetailsScreenState> = infoDetailsState

    init {
        getMovieDetails()
    }
    fun getMovieDetails() {
        if (movieId != null) {
            infoDetailsState.postValue(DetailsScreenState.Loading)

            viewModelScope.launch {
                moviesInteractor
                    .getMovieDetails(movieId)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        } else {
            infoDetailsState.postValue(DetailsScreenState.Error(application.getString(R.string.something_went_wrong)))
        }
    }

    private fun processResult(foundDetails: MovieDetails?, errorMessage: String?) {
        when (foundDetails) {
            null -> infoDetailsState.postValue(
                DetailsScreenState.Error(
                    errorMessage ?: ""
                )
            )
            else -> infoDetailsState.postValue(DetailsScreenState.Content(foundDetails))
        }
    }

}