package ru.me.searchmoviesapp.ui.details.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PosterDetailsViewModel(private val posterUrl: String,): ViewModel() {

    private val posterDetailState = MutableLiveData<String>(posterUrl)
    fun getPosterDetailState(): LiveData<String> = posterDetailState

    fun getPosterUrl() {
        if (posterUrl != null) {
            posterDetailState.postValue(posterUrl!!)
        }
    }
}