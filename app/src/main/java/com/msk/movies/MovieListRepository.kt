package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gojek.assignment.db.MediaDao
import com.msk.movies.dataSource.MovieDataSourceFactory
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem
import com.msk.movies.service.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListRepository @Inject constructor(private val service: Service,
                                              private val daoRepo: MediaDao) {

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


    fun fetchMovieDetails(movieName: String, plot: String, key: String): MutableLiveData<MediaEntity> {

        val moviesListResponse: MutableLiveData<MediaEntity> = MutableLiveData()
        val observable = service.getMovieDetails(movieName, plot, key)

        observable.map<MediaEntity> {
            saveMovieDetailsRecord(it)
            it
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    moviesListResponse.value = it
                },
                {
                    moviesListResponse.value = null
                })

        return moviesListResponse
    }

    private fun saveMovieDetailsRecord(movieDetails: MediaEntity) {
        daoRepo.saveMovieDetailsRecord(movieDetails)
    }

    fun getEntity(mediaId: String) {
        daoRepo.loadMedia(mediaId)
    }

    fun getBookMarkedMovies(): LiveData<List<MediaEntity>> {
        return daoRepo.loadGithubRepoData();
    }

    fun bookMarkMovie(mediaId: String,bookMarked:Boolean=true) {
        daoRepo.updateMovieWithBookMark(bookMarked,mediaId)
    }

    fun deleteEntity(movieId: String) {
        daoRepo.deleteMovie(movieId)
        daoRepo.loadBookMarkedList(true)
    }
}
