package com.msk.movies.di.module

import androidx.room.Room
import com.gojek.assignment.db.MediaDao
import com.gojek.assignment.db.MediaDatabase
import com.msk.movies.MovieApplication
import com.msk.movies.db.OmdbLocalCache
import com.msk.movies.di.BaseUrl
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
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
    fun provideGithubDatabase(application: MovieApplication): MediaDatabase {
        return Room.databaseBuilder(application, MediaDatabase::class.java, "media.db").build()
    }

    @Provides
    @Singleton
    fun provideGithubRepoDao(githubDatabase: MediaDatabase): MediaDao {
        return githubDatabase.mediaDao()
    }

    @Provides
    @Singleton
    fun provideCache(database: MediaDatabase): OmdbLocalCache {
        return OmdbLocalCache(database.mediaDao(), Executors.newSingleThreadExecutor())
    }
}