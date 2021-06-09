package com.msk.movies

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.movies.model.SearchItem

class MovieListAdapter() :
    PagedListAdapter<SearchItem, MovieListAdapter.MovieViewHolder>(NewsDiffCallback) {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row_item, parent, false)

        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val searchItem = getItem(position)
        holder.name.text = searchItem!!.title
        holder.year.text = searchItem.year
        Glide.with(context)
            .load(searchItem.poster)
            .into(holder.movieImageView)

    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.title == newItem.title
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
                //oldItem.equals(newItem)
            }
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val name: TextView = itemView.findViewById<View>(R.id.media_title) as TextView
        internal val year: TextView = itemView.findViewById<View>(R.id.media_year) as TextView
        internal val movieImageView: ImageView =
            itemView.findViewById<View>(R.id.media_image) as ImageView
    }
}
