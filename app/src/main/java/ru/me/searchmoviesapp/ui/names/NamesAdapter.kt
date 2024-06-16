package ru.me.searchmoviesapp.ui.names

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.me.searchmoviesapp.domain.models.Name

class NamesAdapter: RecyclerView.Adapter<NamesViewHolder>() {
    var names = ArrayList<Name>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        return NamesViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        holder.bind(names[position])
    }
}