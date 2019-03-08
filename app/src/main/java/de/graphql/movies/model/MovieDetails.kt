package de.graphql.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieDetails(
    val vote_count: Int,
    val id: Int,
    val video: Boolean,
    val vote_average: Double,
    val title: String,
    val popularity: Double,
    val poster_path: String?,
    val backdrop_path: String? = "",
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val cast_id: Int? = null,
    val character: String? = null,
    val actorId: Int? = null,
    val name: String? = null,
    val order: Int? = null,
    val profile_path: String? = null
) : Parcelable