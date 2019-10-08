package com.msk.movies.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.msk.movies.model.SearchItem
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class OmdbLocalCache(
        private val mediaDao: MediaDao,
        private val ioExecutor: Executor
) {

    /**
     * Insert a list of repos in the database, on a background thread.
     */
    fun insertMedia(repos: PagedList<SearchItem>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("OmdbLocalCache", "inserting ${repos?.size} repos")
            if (repos != null) {
                mediaDao.insertAll(repos)
            }
            insertFinished()
        }
    }

    /**
     * Insert a single record in the database, on a background thread.
     */
    fun insertMovie(repos: SearchItem, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("OmdbLocalCache", "inserting ${repos.title} repos")
            mediaDao.insertMedia(repos)
            insertFinished()
        }
    }

    /**
     * Insert a single record in the database, on a background thread.
     */
    fun updateMovieWithBookMark(query: String, bookMarked: Boolean = true) {
        ioExecutor.execute {
            mediaDao.update(bookMarked, query)
        }
    }


    fun moviesByName(name: String): DataSource.Factory<Int, SearchItem> {
        return mediaDao.loadMediaFromSearch(name)
    }

    fun moviesByMediaId(name: String): SearchItem {
        return mediaDao.loadMedia(name)
    }

    fun getBookMarkedMovies(): LiveData<List<SearchItem>> {
        return mediaDao.loadBookMarkedMedia(true)
    }

}
