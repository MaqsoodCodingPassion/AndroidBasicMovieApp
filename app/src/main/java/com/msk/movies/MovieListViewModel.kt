package com.msk.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.msk.movies.MovieListRepository
import com.msk.movies.model.MovieDetailsResponse
import com.msk.movies.model.SearchItem

class MovieListViewModel(val movieListRepository: MovieListRepository) : ViewModel() {


    private val queryLiveData = MutableLiveData<String>()
    var bookMarkLiveData = movieListRepository.getBookMarkedMovies()

    fun getMovieList(movieName: String, key: String): LiveData<PagedList<SearchItem>> {
        return movieListRepository.getMovieList(movieName, key)
    }

    fun fetchMovieDetails(imdbID: String, plot: String,key: String): LiveData<MovieDetailsResponse> {
        return movieListRepository.fetchMovieDetails(imdbID, plot,key)
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun getBookMarkedMovies() {
        movieListRepository.getBookMarkedMovies()
    }
    fun deleteMovie(mediaId:String) {
        movieListRepository.bookMarkMovie(mediaId,false)
        movieListRepository.getBookMarkedMovies()
    }

    fun bookMarkMovie(movieId: String) {
        movieListRepository.bookMarkMovie(movieId)
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value
}