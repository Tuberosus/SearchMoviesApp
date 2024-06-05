package ru.me.searchmoviesapp.ui.movies_cast.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.me.searchmoviesapp.databinding.ActivityMoviesCastBinding
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.ui.ScreenState
import ru.me.searchmoviesapp.ui.movies_cast.view_model.MovieCastViewModel

class MoviesCastActivity : AppCompatActivity() {

    companion object {
        private const val ARGS_MOVIE_ID = "movie_id"
        fun newInstance(context: Context, movieId: String): Intent {
            return Intent(context, MoviesCastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID, movieId)
            }
        }
    }

    private val binding by lazy { ActivityMoviesCastBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MovieCastViewModel> {
        parametersOf(intent.getStringExtra(ARGS_MOVIE_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeMovieCast().observe(this) {
            when (it) {
                ScreenState.Loading -> {}
                is ScreenState.Error -> {}
                is ScreenState.Content<*> -> {
                    val cast = (it as ScreenState.Content<FullCastData>).content
                    render(cast)
                }
            }
        }
    }

    private fun render(cast: FullCastData) {
        binding.textCastTitle.text = cast.imDbId
    }
}