package com.msk.movies.di.module

import com.msk.movies.MovieApplication
import com.msk.movies.MovieListRepository
import com.msk.movies.di.BaseUrl
import com.msk.movies.service.Service
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: MovieApplication): MovieApplication {
        return application
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "http://www.omdbapi.com"
    }

    @Provides
    @Singleton
    fun provideMovieRepository(service: Service): MovieListRepository {
        return MovieListRepository(service)
    }
}