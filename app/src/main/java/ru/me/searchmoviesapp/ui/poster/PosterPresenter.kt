package ru.me.searchmoviesapp.ui.poster

class PosterPresenter(
    private val view: PosterView,
    private val imageUrl: String,
) {

    fun onCreate() {
        view.setupPosterImage(imageUrl)
    }
}