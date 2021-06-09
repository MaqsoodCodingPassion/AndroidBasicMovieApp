package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(val repository: MovieListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviesListResponse: MutableLiveData<MediaEntity> = MutableLiveData()

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

    fun fetchMovieDetails(movieName: String, plot: String, key: String) {
        compositeDisposable += repository.fetchMovieDetails(movieName, plot, key)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MediaEntity>() {
                override fun onSuccess(data: MediaEntity) {
                    moviesListResponse.value = data
                }

                override fun onError(e: Throwable) {
                    moviesListResponse.value = null
                }
            })
    }

    /*
       Adding disposables using extension function
     */
    operator fun CompositeDisposable.plusAssign(disposable: DisposableSingleObserver<MediaEntity>) {
        add(disposable)
    }

    /*
       clearing all disposables
     */
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}