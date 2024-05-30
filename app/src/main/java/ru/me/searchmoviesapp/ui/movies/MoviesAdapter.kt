package ru.me.searchmoviesapp.ui.movies

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.models.Movie

class MoviesAdapter(private val clickListener: MovieClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onMovieClick(movies.get(position))
        }

        val favorite = holder.itemView.findViewById<ImageView>(R.id.favorite)
        favorite.setOnClickListener {
            clickListener.onFavoriteToggleClick(movies.get(position))
        }
    }

    override fun getItemCount(): Int = movies.size

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
        fun onFavoriteToggleClick(movie: Movie)
    }
}