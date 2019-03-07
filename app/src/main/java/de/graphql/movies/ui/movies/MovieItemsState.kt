package de.graphql.movies.ui.movies

import de.graphql.movies.MoviesQuery

data class MovieItemsState(val data: List<MoviesQuery.Movie>? = null)