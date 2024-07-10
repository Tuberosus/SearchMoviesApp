package ru.me.searchmoviesapp.presentation.names

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.api.SearchNamesUseCase
import ru.me.searchmoviesapp.domain.models.Name

class NamesViewModel(
    private val searchNamesUseCase: SearchNamesUseCase,
    private val application: Application
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val observeLiveData = MutableLiveData<NamesScreenState>()
    fun observer(): LiveData<NamesScreenState> = observeLiveData

    private var searchJob: Job? = null
    private var latestSearchText: String? = null

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            observeLiveData.postValue(NamesScreenState.Loading)
        }

        viewModelScope.launch {
            searchNamesUseCase
                .search(newSearchText)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(foundNames: List<Name>?, errorMessage: String?) {
        val names = mutableListOf<Name>()
        if (foundNames != null) {
            names.addAll(foundNames)
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