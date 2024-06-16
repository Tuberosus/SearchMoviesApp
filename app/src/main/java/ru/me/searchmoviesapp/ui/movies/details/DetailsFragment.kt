package ru.me.searchmoviesapp.ui.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    companion object {
        const val POSTER_URL = "posterUrl"
        const val MOVIE_ID = "movieId"

        fun createArgs(posterUrl: String, movieId: String): Bundle =
            bundleOf(
                POSTER_URL to posterUrl,
                MOVIE_ID to movieId
                )
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
            childFragmentManager,
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

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }
}