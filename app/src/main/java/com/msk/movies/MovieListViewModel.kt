package com.khan.movieskotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.khan.movieskotlin.model.SearchItem
import com.msk.movies.model.MovieDetailsResponse

class MovieListViewModel(val movieListRepository: MovieListRepository) : ViewModel() {

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return movieListRepository.getMovieList(movieName, key)
    }

    fun fetchMovieDetails(imdbID: String, plot: String,key: String): LiveData<MovieDetailsResponse> {
        return movieListRepository.fetchMovieDetails(imdbID, plot,key)
    }
}