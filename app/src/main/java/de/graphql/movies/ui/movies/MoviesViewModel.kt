package de.graphql.movies.ui.movies

import androidx.lifecycle.MutableLiveData
import de.graphql.movies.CoroutineViewModel
import de.graphql.movies.MoviesQuery
import de.graphql.movies.MoviesWithMainActorsQuery
import de.graphql.movies.model.MovieDetails
import de.graphql.movies.utils.ApolloManager
import de.graphql.movies.utils.toMovieDetails

class MoviesViewModel(private val apolloManager: ApolloManager) : CoroutineViewModel() {

    val moviesLiveData: MutableLiveData<List<MovieDetails>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun loadMovies(listSize: Int) {
        apolloManager.enqueueQuery(query = MoviesQuery.builder()
            .size(listSize)
            .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(data.movies()
                        .asSequence()
                        .map { movie ->
                            movie.toMovieDetails()
                        }.toList()
                    )
                }
            },

            onFailure = {
                errorMessage.postValue(it.message)
            })
    }

    fun loadMoviesWithMainActor(year: Int?) {
        apolloManager.enqueueQuery(query = MoviesWithMainActorsQuery.builder()
            .year(year)
            .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(
                        data.moviesWithMainActors()
                            .asSequence()
                            .map { moviesWithMainActor ->
                                moviesWithMainActor.toMovieDetails()
                            }.toList()
                    )
                }
            },
            onFailure = {

            })
    }
}
