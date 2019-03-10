package de.graphql.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CastItem(
    val cast_id: Int,
    val character: String,
    val id: Int,
    val name: String,
    val order: Int,
    val profile_path: String? = null
) : Parcelable