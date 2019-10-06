package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.msk.movies.MovieListRepository
import com.msk.movies.model.MovieDetailsResponse
import com.msk.movies.model.SearchItem

class MovieListViewModel(val movieListRepository: MovieListRepository) : ViewModel() {

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return movieListRepository.getMovieList(movieName, key)
    }

    fun fetchMovieDetails(imdbID: String, plot: String,key: String): LiveData<MovieDetailsResponse> {
        return movieListRepository.fetchMovieDetails(imdbID, plot,key)
    }
}