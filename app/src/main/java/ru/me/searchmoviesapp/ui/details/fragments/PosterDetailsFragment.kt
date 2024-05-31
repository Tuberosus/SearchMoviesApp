package ru.me.searchmoviesapp.ui.details.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.me.searchmoviesapp.databinding.FragmentDetailsPosterBinding
import ru.me.searchmoviesapp.ui.details.activity.DetailsActivity
import ru.me.searchmoviesapp.ui.details.view_model.InfoDetailsViewModel
import ru.me.searchmoviesapp.ui.details.view_model.PosterDetailsViewModel

class PosterDetailsFragment: Fragment() {

    companion object {
        fun newInstance(imageUrl: String?) = PosterDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(DetailsActivity.POSTER_URL, imageUrl)
            }
        }
    }

    private val viewModel by viewModel<PosterDetailsViewModel> {
        parametersOf(requireArguments().getString(DetailsActivity.POSTER_URL))
    }

    private lateinit var binding: FragmentDetailsPosterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPosterDetailState().observe(viewLifecycleOwner) {
            setupPosterImage(it)
        }
    }

    private fun setupPosterImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.poster)
    }
}