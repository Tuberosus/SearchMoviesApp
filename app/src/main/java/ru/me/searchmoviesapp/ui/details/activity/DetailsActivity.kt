package ru.me.searchmoviesapp.ui.details.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.ActivityDetailsBinding
import ru.me.searchmoviesapp.domain.models.MovieDetails
import ru.me.searchmoviesapp.ui.details.DetailsScreenState
import ru.me.searchmoviesapp.ui.details.fragments.DetailsViewPagerAdapter
import ru.me.searchmoviesapp.ui.details.view_model.InfoDetailsViewModel

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val POSTER_URL = "posterUrl"
        const val MOVIE_ID = "movieId"
    }

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val posterUrl = intent.getStringExtra(POSTER_URL).toString()
        val movieId = intent.getStringExtra(MOVIE_ID)
//        Log.d("MyTag", posterUrl)
        val pagerAdapter = DetailsViewPagerAdapter(
                supportFragmentManager,
                lifecycle,
                posterUrl,
                movieId,)

        binding.viewPager.adapter = pagerAdapter

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.about_movie)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }


}