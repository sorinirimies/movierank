package de.graphql.movies.utils

import de.graphql.movies.MoviesQuery
import de.graphql.movies.model.MovieDetails

fun MoviesQuery.Movie.toMovieDetails() =
    MovieDetails(this.title(), this.overview(), this.release_date(), this.vote_average(), this.poster_path())