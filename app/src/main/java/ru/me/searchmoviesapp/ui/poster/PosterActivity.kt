package ru.me.searchmoviesapp.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ru.me.searchmoviesapp.R

class PosterActivity : AppCompatActivity() {

    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        val imageUrl = intent.extras?.getString("poster", "") ?: ""

        poster = findViewById(R.id.poster)
        setupPosterImage(imageUrl)
    }

    private fun setupPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}