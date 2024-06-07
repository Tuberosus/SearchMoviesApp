package ru.me.searchmoviesapp.ui.movies_cast.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.me.searchmoviesapp.databinding.ActivityMoviesCastBinding
import ru.me.searchmoviesapp.ui.movies_cast.view_model.MovieCastViewModel
import ru.me.searchmoviesapp.ui.movies_cast.view_model.MoviesCastState

class MoviesCastActivity : AppCompatActivity() {

    companion object {
        private const val ARGS_MOVIE_ID = "movie_id"
        fun newInstance(context: Context, movieId: String): Intent {
            return Intent(context, MoviesCastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID, movieId)
            }
        }
    }

    private val adapter = MovieCastAdapter()

    private val binding by lazy { ActivityMoviesCastBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MovieCastViewModel> {
        parametersOf(intent.getStringExtra(ARGS_MOVIE_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.observeMovieCast().observe(this) {
            when (it) {
                MoviesCastState.Loading -> showLoading()
                is MoviesCastState.Error -> showError(it)
                is MoviesCastState.Content -> showContent(it)
            }
        }
    }

    private fun showLoading() {
        binding.contentContainer.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.progressBar.isVisible = true
    }

    private fun showError(state: MoviesCastState.Error) {
        binding.contentContainer.isVisible = false
        binding.progressBar.isVisible = false

        binding.errorMessageTextView.isVisible = true
        binding.errorMessageTextView.text = state.message
    }

    private fun showContent(state: MoviesCastState.Content) {
        binding.progressBar.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.contentContainer.isVisible = true
        binding.movieTitle.text = state.fullTitle

        adapter.items = state.items
        adapter.notifyDataSetChanged()
    }
}