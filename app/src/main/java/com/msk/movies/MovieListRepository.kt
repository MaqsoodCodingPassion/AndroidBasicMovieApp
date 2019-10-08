package com.msk.movies

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.msk.movies.dataSource.MovieDataSourceFactory
import com.msk.movies.db.OmdbDatabase
import com.msk.movies.db.OmdbLocalCache
import com.msk.movies.model.MovieDetailsResponse
import com.msk.movies.model.SearchItem
import com.msk.movies.service.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Inject

class MovieListRepository @Inject constructor(private val service: Service,private val app: Application) {

    lateinit var newsList: LiveData<PagedList<SearchItem>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private var newsDataSourceFactory: MovieDataSourceFactory? = null
    private var cache = provideCache(app.applicationContext)

    private fun provideCache(context: Context): OmdbLocalCache {
        val database = OmdbDatabase.getInstance(context)
        return OmdbLocalCache(database.mediaDao(),Executors.newSingleThreadExecutor())
    }

    fun getMovieList(movieName: String, apiKey: String): LiveData<PagedList<SearchItem>> {
        newsDataSourceFactory = MovieDataSourceFactory(compositeDisposable, service, movieName, apiKey)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory!!, config).build()
        Log.d("OmdbLocalCache", "size ${newsList?.value?.size} repos")
       // cache.insertMedia(newsList){}

        Log.d("OmdbLocalCache", "newsList is   ${newsList} ")
        return newsList
    }

    fun fetchMovieDetails(movieName: String, plot: String,key: String): MutableLiveData<MovieDetailsResponse> {

        val moviesListResponse: MutableLiveData<MovieDetailsResponse> = MutableLiveData()
        val observable = service.getMovieDetails(movieName, plot,key)

        observable?.subscribeOn(Schedulers.newThread())?.observeOn(AndroidSchedulers.mainThread())
            ?.map<MovieDetailsResponse> { it!! }
            ?.subscribe(
                Consumer<MovieDetailsResponse> {
                    moviesListResponse.value = it
                },
                Consumer<Throwable> {
                    it.printStackTrace()
                })

        return moviesListResponse
    }


    fun bookMarkMovie(mediaId: String,bookMarked:Boolean=true) {
        cache?.updateMovieWithBookMark(mediaId,bookMarked)
    }

    fun getBookMarkedMovies(): LiveData<List<SearchItem>>? {
        return cache!!.getBookMarkedMovies()
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}
