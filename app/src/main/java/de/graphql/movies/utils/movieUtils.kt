package de.graphql.movies.utils

import de.graphql.movies.*
import de.graphql.movies.model.CastItem
import de.graphql.movies.model.MovieItem

fun LocalDbMoviesQuery.LocalDbMovie.toMovieItem() =
    MovieItem(
        this.vote_count(),
        this.id(),
        this.video(),
        this.vote_average(),
        this.title(),
        this.popularity(),
        this.poster_path(),
        this.backdrop_path(),
        this.adult(),
        this.overview(),
        this.release_date()
    )

fun MoviesByYearQuery.MoviesByYear.toMovieItem() =
    MovieItem(
        this.vote_count(),
        this.id(),
        this.video(),
        this.vote_average(),
        this.title(),
        this.popularity(),
        this.poster_path(),
        this.backdrop_path(),
        this.adult(),
        this.overview(),
        this.release_date()
    )

fun MoviesByYearWithMainActorsQuery.MoviesByYearWithMainActor.toMovieItem() =
    MovieItem(
        this.vote_count(),
        this.id(),
        this.video(),
        this.vote_average(),
        this.title(),
        this.popularity(),
        this.poster_path(),
        this.backdrop_path(),
        this.adult(),
        this.overview(),
        this.release_date(),
        this.cast_id(),
        this.character(),
        this.actorId(),
        this.name(),
        this.order(),
        this.profile_path()
    )

fun MovieCastQuery.MovieCast.toCastItem() = CastItem(
    this.cast_id(),
    this.character(),
    this.id(),
    this.name(),
    this.order(),
    this.profile_path()
)
