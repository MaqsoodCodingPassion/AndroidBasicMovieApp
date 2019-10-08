package com.msk.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem
import com.noonacademy.assignment.omdb.movies.ui.BookMarkMovieViewHolder

/**
 * Adapter for the list of repositories.
 */
class BookMarkMovieAdapter(private var items: List<SearchItem>, private val movieSearchViewModel:MovieListViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookMarkMovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bookmarkedMovie = getItem(position)
        val bookMarkViewHolder = holder as BookMarkMovieViewHolder
        if (bookmarkedMovie != null) {
            bookMarkViewHolder.bind(bookmarkedMovie)
            bookMarkViewHolder.deleteBookMark.setOnClickListener {
                movieSearchViewModel.deleteMovie(bookmarkedMovie.imdbid)
            }
        }
    }

    fun notifyDataSet(items: List<SearchItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): SearchItem? = items[position]


}
