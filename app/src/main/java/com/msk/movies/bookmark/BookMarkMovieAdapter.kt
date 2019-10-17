package com.msk.movies.bookmark

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msk.movies.MovieListViewModel
import com.msk.movies.model.MediaEntity

/**
 * Adapter for the list of repositories.
 */
class BookMarkMovieAdapter(
    private var items: List<MediaEntity>,
    private val mViewModel: MovieListViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                mViewModel.deleteMovie(bookmarkedMovie.imdbID)
            }
        }
    }

    fun notifyDataSet(items: List<MediaEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): MediaEntity? = items[position]


}
