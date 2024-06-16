package ru.me.searchmoviesapp.ui.movies.movies_cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.me.searchmoviesapp.databinding.FragmentMovieCastBinding
import ru.me.searchmoviesapp.presentation.movies_cast.MovieCastViewModel
import ru.me.searchmoviesapp.presentation.movies_cast.MoviesCastState

class MovieCastFragment : Fragment() {

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun createArgs(movieId: String?): Bundle =
            bundleOf(MOVIE_ID to movieId)
    }

    private val adapter = MovieCastAdapter()

    private var _binding: FragmentMovieCastBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MovieCastViewModel> {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieCastBinding.inflate(inflater, container, false)

        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesCastRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeMovieCast().observe(viewLifecycleOwner) {
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