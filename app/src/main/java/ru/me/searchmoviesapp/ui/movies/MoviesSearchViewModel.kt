package ru.me.searchmoviesapp.ui.movies

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.models.Movie
import util.SingleLiveEvent

class MoviesSearchViewModel(
    private val application: Application,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
//        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }

    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var latestSearchText: String? = null

//    private var isClickAllowed = true

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

//    fun clickDebounce(): Boolean {
//        val current = isClickAllowed
//        if (isClickAllowed) {
//            isClickAllowed = false
//            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
//        }
//        return current
//    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    val movies = mutableListOf<Movie>()
                    if (foundMovies != null) {
                        movies.addAll(foundMovies)
                    }

                    when {
                        errorMessage != null -> {
                            renderState(
                                MoviesState.Error(
                                    errorMessage = application.getString(R.string.something_went_wrong),
                                )
                            )
                            showToast.postValue(errorMessage!!)
                        }

                        movies.isEmpty() -> {
                            renderState(
                                MoviesState.Empty(
                                    message = application.getString(R.string.nothing_found),
                                )
                            )
                        }

                        else -> {
                            renderState(
                                MoviesState.Content(
                                    movies = movies,
                                )
                            )
                        }
                    }

                }
            })
        }
    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMovieToFavorites(movie)
        }
        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        // 2
        if (currentState is MoviesState.Content) {
            // 3
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }

            // 4
            if (movieIndex != -1) {
                // 5
                stateLiveData.value = MoviesState.Content(
                    currentState.movies.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        // 1
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                // 2
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavorite })
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
            }
        }
    }

}