package com.msk.movies.service

import com.msk.movies.model.MediaEntity
import com.msk.movies.model.MoviesListResponse
import com.msk.movies.model.impersonate.ImpersonateListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface Service {

    @GET("/api/now/sg/impersonation/users")
    fun getMoviesList(
        @Query("start") start: Int, @Query("filter") filter: String): Single<ImpersonateListResponse>

    @GET("/")
    fun getMovieDetails(
        @Query("i") id: String, @Query("plot") plot: String, @Query("apikey") key: String): Single<MediaEntity>

    /* @GET("/")
    fun getMoviesList(
        @Query("start") start: String, @Query("filter") filter: String,
        @Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<ImpersonateListResponse>

     */
}
