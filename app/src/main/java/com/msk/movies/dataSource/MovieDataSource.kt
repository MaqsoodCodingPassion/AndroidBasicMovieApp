package com.msk.movies.dataSource

import androidx.paging.PageKeyedDataSource
import com.msk.movies.model.impersonate.UsersItem
import com.msk.movies.service.Service
import io.reactivex.disposables.CompositeDisposable

class MovieDataSource(
    private val networkService: Service,
    private val compositeDisposable: CompositeDisposable,
    var start: Int,
    val filter: String
) : PageKeyedDataSource<Int, UsersItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UsersItem>
    ) {
        compositeDisposable.add(
            networkService.getMoviesList(1, filter)
                .subscribe(
                    { response ->
                        response?.users?.let {
                            callback.onResult(
                                it,
                                null,
                                1
                            )
                        }
                    },
                    { it.printStackTrace()}
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UsersItem>) {
        compositeDisposable.add(
            networkService.getMoviesList(params.requestedLoadSize, filter)
                .subscribe(
                    { response ->
                        response?.users?.let {
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UsersItem>) {
    }
}