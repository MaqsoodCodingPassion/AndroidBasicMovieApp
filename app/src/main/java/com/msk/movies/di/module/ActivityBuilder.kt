package com.khan.movieskotlin.di.module

import com.msk.movies.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract fun bindActivityMain(): MainActivity
}