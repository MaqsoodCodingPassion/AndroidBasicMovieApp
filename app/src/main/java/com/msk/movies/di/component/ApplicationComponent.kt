package com.msk.movies.di.component

import com.khan.movieskotlin.di.module.ActivityBuilder
import com.khan.movieskotlin.di.module.ApplicationModule
import com.khan.movieskotlin.di.module.NetworkModule
import com.msk.movies.di.module.FragmentModule
import com.noon.movies.MovieApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (NetworkModule::class),
        (ApplicationModule::class),
        (ActivityBuilder::class),
        (FragmentModule::class)]
)

interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MovieApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: MovieApplication)
}
