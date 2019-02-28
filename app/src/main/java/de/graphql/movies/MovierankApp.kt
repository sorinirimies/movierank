package de.graphql.movies

import android.app.Application
import de.graphql.movies.injection.networkModule
import de.graphql.movies.injection.viewModelsModule
import org.koin.core.context.startKoin
import timber.log.Timber

class MovierankApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin {
            logger()
            modules(
                networkModule,
                viewModelsModule
            )
        }
    }
}