package com.msk.movies.di.module

import com.msk.movies.fragments.HomeFragment
import com.msk.movies.fragments.MovieDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment
}