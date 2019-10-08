package com.noonacademy.assignment.omdb.movies.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.msk.movies.R
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem

/**
 * View Holder for a [MediaEntity] RecyclerView list item.
 */
class BookMarkMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.media_title)
    private val mediaPoster: ImageView = view.findViewById(R.id.poster)
    val deleteBookMark: ImageView = view.findViewById(R.id.delete)
    private var media: SearchItem? = null

    fun bind(movie: SearchItem?) {
        if (movie == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        } else {
            showMovieData(movie)
        }
    }

    private fun showMovieData(media: SearchItem) {
        this.media = media
        name.text = media.title

        Glide.with(context)
            .load(media.poster)
            .into(mediaPoster)
        //Glide.get().load(media.poster).into(mediaPoster)
    }

    companion object {
        var context :Context?  = null
        fun create(parent: ViewGroup): BookMarkMovieViewHolder {
            context = parent.context as Nothing?
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.bookmark_movie_item, parent, false)
            return BookMarkMovieViewHolder(view)
        }
    }
}
