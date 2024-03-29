package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.msk.movies.dataSource.MovieDataSourceFactory
import com.msk.movies.db.MediaLocalCache
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem
import com.msk.movies.service.Service
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val service: Service,
    private val cache: MediaLocalCache) {

    lateinit var newsList: LiveData<PagedList<SearchItem>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private var newsDataSourceFactory: MovieDataSourceFactory? = null

    fun getMovieList(movieName: String, apiKey: String): LiveData<PagedList<SearchItem>> {
        newsDataSourceFactory =
            MovieDataSourceFactory(compositeDisposable, service, movieName, apiKey)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory!!, config).build()
        return newsList
    }

    fun fetchMovieDetails(movieName: String, plot: String, key: String): Single<MediaEntity> {
        return service.getMovieDetails(movieName, plot, key)
    }

    fun saveMovieDetailsRecord(movieDetails: MediaEntity) {
        cache.insertMovie(movieDetails)
    }

    fun getEntity(mediaId: String): LiveData<MediaEntity> {
        return cache.movieByMediaId(mediaId)
    }

    fun getBookMarkedMovies(): LiveData<List<MediaEntity>> {
        return cache.getBookMarkedMovies()
    }

    fun bookMarkMovie(mediaId: String) {
        cache.updateMovieWithBookMark(mediaId)
    }

    fun deleteEntity(movieId: String) {
        cache.deleteRecordById(movieId)
    }
}
