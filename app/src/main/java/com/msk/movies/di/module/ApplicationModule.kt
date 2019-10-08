package com.msk.movies.di.module

import android.content.Context
import com.msk.movies.MovieApplication
import com.msk.movies.MovieListRepository
import com.msk.movies.db.OmdbLocalCache
import com.msk.movies.di.BaseUrl
import com.msk.movies.di.MyApplicationScope
import com.msk.movies.service.Service
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "http://www.omdbapi.com"
    }

    @Provides
    @Singleton
    fun provideMovieRepository(service: Service,application: MovieApplication): MovieListRepository {
        return MovieListRepository(service,application)
    }
}