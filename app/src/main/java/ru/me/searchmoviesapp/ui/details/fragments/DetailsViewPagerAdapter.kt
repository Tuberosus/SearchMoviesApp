package ru.me.searchmoviesapp.ui.details.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val posterUrl: String?,
    private val movieId: String?,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        Log.d("MyTag", posterUrl.toString()+" pager adapter")
        return when(position) {
            0 -> PosterDetailsFragment.newInstance(posterUrl)
            else -> InfoDetailsFragment.newInstance(movieId)
        }
    }

}