package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json
import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.utils.parseApiDate
import java.util.*

data class Movie(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "release_date") val releaseDateString: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "genres") val genres: List<Genre>?,
    @Json(name = "runtime") val durationMinutes: Int?,
    @Json(name = "vote_average") val averageVote: Float
) {

    val posterUrl: String?
        get() = posterPath?.let { BuildConfig.IMAGE_URL + it }

    val releaseDate: Date?
        get() = releaseDateString?.parseApiDate()

    val formattedDuration: String?
        get() = durationMinutes?.let {
            val hours = it / 60
            val minutes = it % 60
            return if (minutes == 0) "${hours}h" else "${hours}h ${minutes}m"
        }

    val releaseYearString: String?
        get() = releaseDateString?.split("-")?.get(0)
}
