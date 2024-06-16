package ru.me.searchmoviesapp.presentation.names

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.api.SearchNamesUseCase
import ru.me.searchmoviesapp.domain.models.Name

class NamesViewModel(
    private val searchNamesUseCase: SearchNamesUseCase,
    private val application: Application
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }

    private val handler = Handler(Looper.getMainLooper())

    private val observeLiveData = MutableLiveData<NamesScreenState>()
    fun observer(): LiveData<NamesScreenState> = observeLiveData


    private var latestSearchText: String? = null

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable {
            searchRequest(changedText)
        }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            observeLiveData.postValue(NamesScreenState.Loading)
        }

        searchNamesUseCase.search(newSearchText) { foundActors, errorMessage ->
            val names = mutableListOf<Name>()
            if (foundActors != null) {
                names.addAll(foundActors)
            }

            when {
                errorMessage != null -> {
                    observeLiveData.postValue(
                        NamesScreenState.Error(
                            message = application.getString(R.string.something_went_wrong)
                        )
                    )
                }

                names.isEmpty() -> {
                    observeLiveData.postValue(
                        NamesScreenState.Empty(
                            message = application.getString(R.string.nothing_found)
                        )
                    )
                }

                else -> {
                    observeLiveData.postValue(
                        NamesScreenState.Content(
                            names = names
                        )
                    )
                }
            }

        }
    }

}