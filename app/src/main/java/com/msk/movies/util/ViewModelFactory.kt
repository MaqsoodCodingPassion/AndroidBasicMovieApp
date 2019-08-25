package com.msk.movies.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khan.movieskotlin.MovieListRepository
import com.khan.movieskotlin.MovieListViewModel
import javax.inject.Inject

class ViewModelFactory
    @Inject constructor(private val repository: MovieListRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieListViewModel::class.java) -> MovieListViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}