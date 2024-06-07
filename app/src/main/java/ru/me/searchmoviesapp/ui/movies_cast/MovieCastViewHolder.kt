package ru.me.searchmoviesapp.ui.movies_cast

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.me.searchmoviesapp.R

class MovieCastViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_cast, parent, false)
) {
    val actorImage: ImageView = itemView.findViewById(R.id.actorImageView)
    val personName: TextView = itemView.findViewById(R.id.actorNameTextView)
    val personDescription: TextView = itemView.findViewById(R.id.actorDescriptionTextView)

    fun bind(item: MoviesCastRVItem.PersonItem) {
        if (item.data.image == null) {
            actorImage.isVisible = false
        } else {
            Glide.with(itemView)
                .load(item.data.image)
                .into(actorImage)
            actorImage.isVisible = true
        }
        personName.text = item.data.name
        personDescription.text = item.data.description

    }
}