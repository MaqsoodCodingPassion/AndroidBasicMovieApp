package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.SearchItem

class MovieListViewModel(val repository: MovieListRepository) : ViewModel() {

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return repository.getMovieList(movieName, key)
    }

    fun fetchMovieDetails(imdbID: String, plot: String,key: String): LiveData<MediaEntity> {
        return repository.fetchMovieDetails(imdbID, plot,key)
    }
}