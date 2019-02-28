package de.graphql.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(val id: String, val name: String, val rank: Float) : Parcelable