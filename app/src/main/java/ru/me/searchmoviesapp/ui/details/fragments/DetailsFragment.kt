package ru.me.searchmoviesapp.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    companion object {
        const val POSTER_URL = "posterUrl"
        const val MOVIE_ID = "movieId"

        fun newInstance(posterUrl: String, movieId: String) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(POSTER_URL, posterUrl)
                putString(MOVIE_ID, movieId)
            }
        }
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val posterUrl = requireArguments().getString(POSTER_URL)
        val movieId = requireArguments().getString(MOVIE_ID)
        val pagerAdapter = DetailsViewPagerAdapter(
            parentFragmentManager,
            lifecycle,
            posterUrl,
            movieId,
        )

        binding.viewPager.adapter = pagerAdapter

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.about_movie)
            }
        }
        tabMediator.attach()
    }

    override fun onDetach() {
        super.onDetach()
        tabMediator.detach()
    }
}