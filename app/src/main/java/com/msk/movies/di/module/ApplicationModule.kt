package com.msk.movies.di.module

import androidx.room.Room
import com.msk.movies.MovieApplication
import com.msk.movies.db.MediaDao
import com.msk.movies.db.MediaDatabase
import com.msk.movies.db.MediaLocalCache
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
        return "https://mobilesdoffline.service-now.com"
    }

    @Provides
    @Singleton
    fun provideMediaDatabase(application: MovieApplication): MediaDatabase {
        return Room.databaseBuilder(application, MediaDatabase::class.java, "media.db").build()
    }

    @Provides
    @Singleton
    fun provideMediaDao(database: MediaDatabase): MediaDao {
        return database.mediaDao()
    }

    @Provides
    @Singleton
    fun provideMediaLocalCache(database: MediaDatabase): MediaLocalCache {
        return MediaLocalCache(database.mediaDao(), Executors.newSingleThreadExecutor())
    }
}