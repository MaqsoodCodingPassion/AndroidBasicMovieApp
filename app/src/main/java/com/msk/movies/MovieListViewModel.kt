package com.khan.movieskotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.khan.movieskotlin.model.SearchItem

class MovieListViewModel(val movieListRepository: MovieListRepository) : ViewModel() {

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return movieListRepository.getMovieList(movieName, key)
    }
}