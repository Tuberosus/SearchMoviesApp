package ru.me.searchmoviesapp.ui.movies_cast.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.api.MoviesInteractor
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.ui.movies_cast.MoviesCastRVItem

class MovieCastViewModel(
    private val application: Application,
    private val moviesInteractor: MoviesInteractor,
    private val movieId: String
) : ViewModel() {

    private val movieCastLiveData = MutableLiveData<MoviesCastState>()
    fun observeMovieCast(): LiveData<MoviesCastState> = movieCastLiveData

    init {
        getMovieCast()
    }

    private fun getMovieCast() {
        if (movieId != null) {
            movieCastLiveData.postValue(MoviesCastState.Loading)

            moviesInteractor.getFullCast(
                movieId = movieId,
                consumer = object : MoviesInteractor.CastConsumer {
                    override fun consume(foundCast: FullCastData?, errorMessage: String?) {
                        when (foundCast){
                            null -> movieCastLiveData.postValue(MoviesCastState.Error(errorMessage ?: ""))
                            else -> movieCastLiveData.postValue(castToUiStateContent(foundCast))
                        }
                    }
                }
            )
        } else {
            movieCastLiveData.postValue(MoviesCastState.Error(application.getString(R.string.something_went_wrong)))
        }
    }

    private fun castToUiStateContent(cast: FullCastData): MoviesCastState {
        // Строим список элементов RecyclerView
        val items = buildList<MoviesCastRVItem> {
            // Если есть хотя бы один режиссёр, добавим заголовок
            if (cast.directors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один сценарист, добавим заголовок
            if (cast.writers.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один актёр, добавим заголовок
            if (cast.actors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один дополнительный участник, добавим заголовок
            if (cast.others.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Others")
                this += cast.others.map { MoviesCastRVItem.PersonItem(it) }
            }
        }
        return MoviesCastState.Content(
            fullTitle = cast.fullTitle,
            items = items
        )
    }
}