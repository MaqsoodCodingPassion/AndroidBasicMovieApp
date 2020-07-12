package com.msk.movies.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.msk.movies.model.impersonate.UsersItem
import com.msk.movies.service.Service
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: Service,
    private val start: Int,
    private val filter: String)
    : DataSource.Factory<Int, UsersItem>() {

    val newsDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, UsersItem> {
        val newsDataSource = MovieDataSource(networkService, compositeDisposable,start,filter)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}