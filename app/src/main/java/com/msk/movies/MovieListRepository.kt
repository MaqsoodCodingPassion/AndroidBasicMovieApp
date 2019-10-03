package com.khan.movieskotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.architecture.paging.MovieDataSourceFactory
import com.khan.movieskotlin.model.MoviesListResponse
import com.khan.movieskotlin.model.SearchItem
import com.msk.movies.model.MovieDetailsResponse
import com.msk.movies.service.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListRepository @Inject constructor(private val service: Service) {

    lateinit var newsList: LiveData<PagedList<SearchItem>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private var newsDataSourceFactory: MovieDataSourceFactory? = null

    fun getMovieList(movieName: String, apiKey: String): LiveData<PagedList<SearchItem>> {
        newsDataSourceFactory = MovieDataSourceFactory(compositeDisposable, service, movieName, apiKey)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory!!, config).build()
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
}
