package com.msk.movies.dataSource

import androidx.paging.PageKeyedDataSource
import com.msk.movies.model.SearchItem
import com.msk.movies.service.Service
import io.reactivex.disposables.CompositeDisposable

class MovieDataSource(
    private val networkService: Service,
    private val compositeDisposable: CompositeDisposable,
    private val movieName: String,
    private val apiKey: String
) : PageKeyedDataSource<Int, SearchItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchItem>
    ) {
        compositeDisposable.add(
            networkService.getMoviesList(movieName, apiKey, 1, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        response?.search?.let {
                            callback.onResult(
                                it,
                                null,
                                2
                            )
                        }
                    },
                    { it.printStackTrace() }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        compositeDisposable.add(
            networkService.getMoviesList(movieName, apiKey, params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        response?.search?.let {
                            callback.onResult(
                                it,
                                params.key + 1
                            )
                        }
                    },
                    { it.printStackTrace() }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
    }
}