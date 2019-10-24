package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(val repository: MovieListRepository) : ViewModel() {

    var bookMarkLiveData = repository.getBookMarkedMovies()

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return repository.getMovieList(movieName, key)
    }

    fun getEntity(movieId: String): LiveData<MediaEntity> {
        return repository.getEntity(movieId)
    }

    fun bookMarkMovie(movieId: String) {
        repository.bookMarkMovie(movieId)
    }

    fun getBookMarkedMovies() {
        repository.getBookMarkedMovies()
    }

    fun deleteMovie(movieId: String?) {
        repository.deleteEntity(movieId!!)
    }

    fun saveMovieDetailsRecord(mediaEntity: MediaEntity) {
        repository.saveMovieDetailsRecord(mediaEntity)
    }

    fun fetchMovieDetails(
        movieName: String,
        plot: String,
        key: String
    ): MutableLiveData<MediaEntity> {

        val moviesListResponse: MutableLiveData<MediaEntity> = MutableLiveData()
        val observable = repository.fetchMovieDetails(movieName, plot, key)

        observable.map<MediaEntity> {
            //saveMovieDetailsRecord(it)
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
}