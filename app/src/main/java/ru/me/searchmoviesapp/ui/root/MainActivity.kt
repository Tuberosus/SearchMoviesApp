package ru.me.searchmoviesapp.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.ActivityMainBinding
import ru.me.searchmoviesapp.ui.movies.SearchMovieFragment

class MainActivity: AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.fragment_main_container_view,
                    SearchMovieFragment.newInstance()
                )
            }
        }
    }
}