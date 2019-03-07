package de.graphql.movies.injection

import com.apollographql.apollo.ApolloClient
import de.graphql.movies.utils.ApolloManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber

private const val BASE_URL = "http://10.0.2.2:9596/graphql"
const val TMDB_BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
val networkModule: Module = module {
    single {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
                .apply { level = HttpLoggingInterceptor.Level.BODY }).build()
    }
    single {
        ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(get())
            .build()
    }
    single {
        ApolloManager(get())
    }
}