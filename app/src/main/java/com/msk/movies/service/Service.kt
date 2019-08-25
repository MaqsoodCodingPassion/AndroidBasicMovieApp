package com.msk.movies.service

import com.khan.movieskotlin.model.MoviesListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("/")
    fun getMoviesList(
        @Query("s") movieName: String, @Query("apikey") key: String,
        @Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<MoviesListResponse>
}
