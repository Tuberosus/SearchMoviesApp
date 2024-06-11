package ru.me.searchmoviesapp.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import org.koin.android.ext.android.inject
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.core.navigation.NavigatorHolder
import ru.me.searchmoviesapp.core.navigation.NavigatorImpl
import ru.me.searchmoviesapp.databinding.ActivityRootBinding
import ru.me.searchmoviesapp.ui.movies.SearchMovieFragment

class RootActivity: AppCompatActivity() {

    private val binding by lazy { ActivityRootBinding.inflate(layoutInflater) }
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = NavigatorImpl(
        fragmentContainerViewId = R.id.rootFragmentContainerView,
        fragmentManager = supportFragmentManager
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
                navigator.openFragment(
                    SearchMovieFragment.newInstance()
                )
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.attachNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.detachNavigator()
    }
}