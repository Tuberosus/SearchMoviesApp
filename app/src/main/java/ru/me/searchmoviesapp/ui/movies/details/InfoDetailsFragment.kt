package ru.me.searchmoviesapp.ui.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.FragmentDetailsInfoBinding
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.presentation.details.DetailsScreenState
import ru.me.searchmoviesapp.presentation.details.InfoDetailsViewModel
import ru.me.searchmoviesapp.ui.movies.movies_cast.MovieCastFragment

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
        parametersOf(requireArguments().getString(MOVIE_ID))
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
                DetailsScreenState.Loading -> {
                    showLoading()
                }

                is DetailsScreenState.Error -> {
                    showError(it.errorMessage)
                }

                is DetailsScreenState.Content -> {
                    showContent(it.movieDetails)
                }
            }
        }

        binding.BtnToCast.setOnClickListener {
            findNavController().navigate(
                R.id.action_detailsFragment_to_movieCastFragment,
                MovieCastFragment.createArgs(requireArguments().getString(MOVIE_ID))
                )
        }
    }

    private fun showError(errorMessage: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            errorText.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.apply {
            errorText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showContent(movieDetails: MovieDetails) {
        binding.apply {
            errorText.visibility = View.GONE
            progressBar.visibility = View.GONE
            informationView.visibility = View.VISIBLE
            title.text = movieDetails.title
            imDbRatingText.text = movieDetails.imDbRating
            yearText.text = movieDetails.year
            countriesText.text = movieDetails.countries
            genresText.text = movieDetails.genres
            directorsText.text = movieDetails.directors
            writersText.text = movieDetails.writers
            starsText.text = movieDetails.stars
            plotText.text = movieDetails.plot
        }
    }
}