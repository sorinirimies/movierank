package de.graphql.movies.ui.movies

import androidx.lifecycle.MutableLiveData
import de.graphql.movies.CoroutineViewModel
import de.graphql.movies.LocalDbMoviesQuery
import de.graphql.movies.MoviesByYearQuery
import de.graphql.movies.MoviesByYearWithMainActorsQuery
import de.graphql.movies.model.MovieItem
import de.graphql.movies.utils.ApolloManager
import de.graphql.movies.utils.toMovieItem

class MoviesViewModel(private val apolloManager: ApolloManager) : CoroutineViewModel() {

    internal val moviesLiveData: MutableLiveData<List<MovieItem>> = MutableLiveData()
    internal val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    fun loadMoviesFromDb(listSize: Int) {
        apolloManager.enqueueQuery(
            query = LocalDbMoviesQuery.builder()
                .size(listSize)
                .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(
                        data.localDbMovies()
                            .asSequence()
                            .map { movie ->
                                movie.toMovieItem()
                            }.toList()
                    )
                }
            },
            onFailure = { errorMessageLiveData.postValue(it.message) })
    }

    fun loadMoviesByYear(year: Int?) {
        apolloManager.enqueueQuery(query = MoviesByYearQuery.builder()
            .year(year)
            .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(
                        data.moviesByYear()
                            .asSequence()
                            .map { movies ->
                                movies.toMovieItem()
                            }.toList()
                    )
                }
            },
            onFailure = { errorMessageLiveData.postValue(it.message) })
    }

    fun loadMoviesByYearWithMainActor(year: Int?) {
        apolloManager.enqueueQuery(
            query = MoviesByYearWithMainActorsQuery.builder()
                .year(year)
                .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(
                        data.moviesByYearWithMainActors()
                            .asSequence()
                            .map { moviesWithMainActor ->
                                moviesWithMainActor.toMovieItem()
                            }.toList()
                    )
                }
            },
            onFailure = { errorMessageLiveData.postValue(it.message) })
    }
}
