package de.graphql.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Movie(val name: String, val year: Date, val rank: Float, val imgUrl: String): Parcelable