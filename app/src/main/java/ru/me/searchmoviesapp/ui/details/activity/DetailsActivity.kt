package ru.me.searchmoviesapp.ui.details.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.ActivityDetailsBinding
import ru.me.searchmoviesapp.ui.details.fragments.DetailsViewPagerAdapter

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