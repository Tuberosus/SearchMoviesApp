package ru.me.searchmoviesapp.ui.movies_cast.activity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.me.searchmoviesapp.R

class MovieCastAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = emptyList<MoviesCastRVItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_item_header -> MovieCastHeaderViewHolder(parent)
            R.layout.list_item_cast -> MovieCastViewHolder(parent)
            else -> error("Unknown viewType create [$viewType]")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            R.layout.list_item_header -> {
                val headerHolder = holder as MovieCastHeaderViewHolder
                headerHolder.bind(items.get(position) as MoviesCastRVItem.HeaderItem)
            }

            R.layout.list_item_cast -> {
                val headerHolder = holder as MovieCastViewHolder
                headerHolder.bind(items.get(position) as MoviesCastRVItem.PersonItem)
            }

            else -> error("Unknown viewType bind [${holder.itemViewType}]")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position)) {
            is MoviesCastRVItem.HeaderItem -> R.layout.list_item_header
            is MoviesCastRVItem.PersonItem -> R.layout.list_item_cast
        }
    }
}