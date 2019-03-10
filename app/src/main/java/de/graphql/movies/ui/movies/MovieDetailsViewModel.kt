package de.graphql.movies.ui.movies

import androidx.lifecycle.MutableLiveData
import de.graphql.movies.CoroutineViewModel
import de.graphql.movies.MovieCastQuery
import de.graphql.movies.model.CastItem
import de.graphql.movies.utils.ApolloManager
import de.graphql.movies.utils.toCastItem

class MovieDetailsViewModel(private val apolloManager: ApolloManager) : CoroutineViewModel() {

    internal val movieCastLiveData: MutableLiveData<List<CastItem>> = MutableLiveData()
    internal val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    fun loadMovieCast(movieId: Int) {
        apolloManager.enqueueQuery(query = MovieCastQuery.builder()
            .movieId(movieId)
            .build(),
            onResponse = {
                it.data()?.let { castData ->
                    movieCastLiveData.postValue(
                        castData.movieCast()
                            .asSequence()
                            .map { cast -> cast.toCastItem() }
                            .toList())
                }
            },
            onFailure = { errorMessageLiveData.postValue(it.message) })
    }
}
