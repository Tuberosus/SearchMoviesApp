package ru.me.searchmoviesapp.ui.names

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.me.searchmoviesapp.R
import ru.me.searchmoviesapp.domain.models.Name

class NamesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_name, parent, false)
) {

    private val image = itemView.findViewById<ImageView>(R.id.image)
    private val name = itemView.findViewById<TextView>(R.id.name)
    private val role = itemView.findViewById<TextView>(R.id.role)

    fun bind(name: Name) {
        Glide.with(itemView)
            .load(name.image)
            .placeholder(R.drawable.ic_person)
            .circleCrop()
            .into(image)

        this.name.text = name.title
        role.text = name.description
    }
}