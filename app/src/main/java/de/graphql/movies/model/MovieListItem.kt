package de.graphql.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class MovieListItem(val title: String, val date: Date, val rank: Float, val imgUrl: String): Parcelable