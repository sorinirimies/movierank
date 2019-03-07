package de.graphql.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieDetails(
    val title: String,
    val description: String,
    val date: String,
    val voteAverage: Double,
    val posterUrl: String? = ""
) : Parcelable