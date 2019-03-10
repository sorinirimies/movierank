package de.graphql.movies.ui.movies

import androidx.lifecycle.MutableLiveData
import de.graphql.movies.CoroutineViewModel
import de.graphql.movies.MoviesQuery
import de.graphql.movies.MoviesWithMainActorsQuery
import de.graphql.movies.model.MovieItem
import de.graphql.movies.utils.ApolloManager
import de.graphql.movies.utils.toMovieItem

class MoviesViewModel(private val apolloManager: ApolloManager) : CoroutineViewModel() {

    internal val moviesLiveData: MutableLiveData<List<MovieItem>> = MutableLiveData()
    internal val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    fun loadMovies(listSize: Int) {
        apolloManager.enqueueQuery(
            query = MoviesQuery.builder()
                .size(listSize)
                .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(
                        data.movies()
                            .asSequence()
                            .map { movie ->
                                movie.toMovieItem()
                            }.toList()
                    )
                }
            },
            onFailure = { errorMessageLiveData.postValue(it.message) })
    }

    fun loadMoviesWithMainActor(year: Int?) {
        apolloManager.enqueueQuery(
            query = MoviesWithMainActorsQuery.builder()
                .year(year)
                .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(
                        data.moviesWithMainActors()
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
