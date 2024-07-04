package ru.me.searchmoviesapp.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.databinding.ActivityRootBinding

class RootActivity: AppCompatActivity() {

    private val binding by lazy { ActivityRootBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.detailsFragment-> bottomNavigationView.visibility = View.GONE
                R.id.movieCastFragment -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    fun animateBottomNavigationView() {
        binding.bottomNavigationView.visibility = View.GONE
    }
}