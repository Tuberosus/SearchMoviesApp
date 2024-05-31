package ru.me.searchmoviesapp.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.me.searchmoviesapp.databinding.FragmentDetailsInfoBinding
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.ui.details.DetailsScreenState
import ru.me.searchmoviesapp.ui.details.activity.DetailsActivity
import ru.me.searchmoviesapp.ui.details.view_model.InfoDetailsViewModel

class InfoDetailsFragment : Fragment() {

    companion object {
        private const val MOVIE_ID = "movieId"
        fun newInstance(movieID: String?) = InfoDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(MOVIE_ID, movieID)
            }
        }
    }

    private val viewModel by viewModel<InfoDetailsViewModel> {
        parametersOf(requireArguments().getString(DetailsActivity.MOVIE_ID))
    }

    private lateinit var binding: FragmentDetailsInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInfoDetailsState().observe(viewLifecycleOwner) {
            when (it) {
                DetailsScreenState.Loading -> {}
                is DetailsScreenState.Error -> {}
                is DetailsScreenState.Content -> {showContent(it.movieDetails)}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        arguments = null
    }

    private fun showError(errorMessage: String) {

    }

    private fun showContent(movieDetails: MovieDetails) {
        binding.title.text = movieDetails.title
        binding.imDbRatingText.text = movieDetails.imDbRating
        binding.yearText.text = movieDetails.year
        binding.countriesText.text = movieDetails.countries
        binding.genresText.text = movieDetails.genres
        binding.directorsText.text = movieDetails.directors
        binding.writersText.text = movieDetails.writers
        binding.starsText.text = movieDetails.stars
        binding.plotText.text = movieDetails.plot
    }
}