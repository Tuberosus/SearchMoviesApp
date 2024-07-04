package ru.me.searchmoviesapp.ui.movies.movies_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.FragmentSearchMovieBinding
import ru.me.searchmoviesapp.domain.models.Movie
import ru.me.searchmoviesapp.presentation.movies_search.MoviesSearchViewModel
import ru.me.searchmoviesapp.presentation.movies_search.MoviesState
import ru.me.searchmoviesapp.ui.movies.details.DetailsFragment
import ru.me.searchmoviesapp.ui.root.RootActivity
import util.debounce

class SearchMovieFragment : Fragment() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
    }

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var onMovieClickDebounce: (Movie) -> Unit

    private lateinit var textWatcher: TextWatcher

    private val viewModel by viewModel<MoviesSearchViewModel>()

    private var adapter: MoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onMovieClickDebounce = debounce<Movie>(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { movie ->
            findNavController().navigate(R.id.action_searchMovieFragment_to_detailsFragment,
                DetailsFragment.createArgs(
                    posterUrl = movie.image,
                    movieId = movie.id
                ))
        }

        adapter = MoviesAdapter(object : MoviesAdapter.MovieClickListener{
            override fun onMovieClick(movie: Movie) {
                (activity as RootActivity).animateBottomNavigationView()
                onMovieClickDebounce(movie)
            }

            override fun onFavoriteToggleClick(movie: Movie) {
                viewModel.toggleFavorite(movie)
            }

        })

        binding.movieListRV.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.movieListRV.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.queryInput.addTextChangedListener(textWatcher)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeShowToast().observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        binding.queryInput.removeTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        textWatcher.let { binding.queryInput.removeTextChangedListener(it) }
    }
    private fun showToast(additionalMessage: String) {
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_LONG).show()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Empty -> showEmpty(state.message)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.movieListRV.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.movieListRV.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(movies: List<Movie>) {
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.movieListRV.visibility = View.VISIBLE

        adapter?.movies?.clear()
        adapter?.movies?.addAll(movies)
        adapter?.notifyDataSetChanged()
    }
}