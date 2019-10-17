package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem

class MovieListViewModel(val repository: MovieListRepository) : ViewModel() {

    var bookMarkLiveData = repository.getBookMarkedMovies()

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return repository.getMovieList(movieName, key)
    }

    fun fetchMovieDetails(imdbID: String, plot: String,key: String): LiveData<MediaEntity> {
        return repository.fetchMovieDetails(imdbID, plot,key)
    }

    fun getEntity(movieId: String) {
        repository.getEntity(movieId)
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
}