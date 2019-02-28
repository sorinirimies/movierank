package de.graphql.movies.injection

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber

private const val BASE_URL = "http://localhost:9596/graphql"

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
}