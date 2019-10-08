package com.msk.movies.di.module

import android.content.Context
import com.msk.movies.MovieListRepository
import com.msk.movies.di.MyApplicationScope
import com.msk.movies.service.Service
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @MyApplicationScope
    fun provideContext(): Context {
        return context
    }
}