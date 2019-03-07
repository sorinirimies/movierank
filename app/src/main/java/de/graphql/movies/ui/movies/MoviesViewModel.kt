package de.graphql.movies.ui.movies

import androidx.lifecycle.MutableLiveData
import de.graphql.movies.CoroutineViewModel
import de.graphql.movies.MoviesQuery
import de.graphql.movies.utils.ApolloManager

class MoviesViewModel(private val apolloManager: ApolloManager) : CoroutineViewModel() {

    val moviesLiveData: MutableLiveData<MovieItemsState> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun loadMovies(listSize: Int) {
        apolloManager.enqueueQuery(query = MoviesQuery.builder()
            .size(listSize)
            .build(),
            onResponse = {
                it.data()?.let { data ->
                    moviesLiveData.postValue(MovieItemsState(data.movies()))
                }
            },

            onFailure = {
                errorMessage.postValue(it.message)
            })
    }
}
