package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json
import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.utils.parseApiDate
import java.util.*

data class Movie(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "release_date") val releaseDateString: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val averageVote: Float
) {

    val posterUrl: String?
        get() = posterPath?.let { BuildConfig.IMAGE_URL + it }

    val releaseDate: Date
        get() = releaseDateString.parseApiDate()
}
