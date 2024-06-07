package ru.me.searchmoviesapp.ui.movies

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.FragmentSearchMovieBinding
import ru.me.searchmoviesapp.domain.models.Movie
import ru.me.searchmoviesapp.ui.details.fragments.DetailsFragment

class SearchMovieFragment : Fragment() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L

        fun newInstance() = SearchMovieFragment()
    }

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var textWatcher: TextWatcher

    private val viewModel by viewModel<MoviesSearchViewModel>()

    private lateinit var adapter: MoviesAdapter

    private var isClickAllowed = true

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

        adapter = MoviesAdapter(
            object : MoviesAdapter.MovieClickListener {
                override fun onMovieClick(movie: Movie) {
                    if (clickDebounce()) {
                        if (savedInstanceState == null) {
                            parentFragmentManager.commit {
                                replace(
                                    R.id.fragment_main_container_view,
                                    DetailsFragment.newInstance(movie.image, movie.id)
                                )
                                addToBackStack(null)
                                setReorderingAllowed(true)
                            }
                        }
                    }
                }
                override fun onFavoriteToggleClick(movie: Movie) {
                    viewModel.toggleFavorite(movie)
                }
            }
        )

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
        binding.movieListRV.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
}