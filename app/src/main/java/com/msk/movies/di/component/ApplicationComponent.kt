package com.msk.movies.di.component

import android.content.Context
import com.msk.movies.MovieApplication
import com.msk.movies.di.MyApplicationScope
import com.msk.movies.di.module.*
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
