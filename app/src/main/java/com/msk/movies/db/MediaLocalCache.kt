package com.msk.movies.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.gojek.assignment.db.MediaDao
import com.msk.movies.model.MediaEntity
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class MediaLocalCache(
    private val mediaDao: MediaDao,
    private val ioExecutor: Executor) {

    /**
     * Insert a single record in the database, on a background thread.
     */
    fun insertMovie(repos: MediaEntity) {
        ioExecutor.execute {
            Log.d("MediaLocalCache", "inserting ${repos.title} repos")
            mediaDao.saveMovieDetailsRecord(repos)
        }
    }

    /**
     * Insert a single record in the database, on a background thread.
     */
    fun updateMovieWithBookMark(query: String, bookMarked: Boolean = true) {
        ioExecutor.execute {
            mediaDao.updateMovieWithBookMark(bookMarked, query)
        }
    }

    fun movieByMediaId(movieId: String): LiveData<MediaEntity> {
        return mediaDao.loadMedia(movieId)
    }

    fun getBookMarkedMovies(): LiveData<List<MediaEntity>> {
        return mediaDao.loadBookMarkedList(true)
    }

    fun deleteRecordById(movieId: String) {
        ioExecutor.execute {
            mediaDao.deleteMovie(movieId)
        }
    }
}
