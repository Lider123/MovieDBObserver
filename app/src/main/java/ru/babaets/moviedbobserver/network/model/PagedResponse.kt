package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json

data class PagedResponse<T>(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val items: List<T>,
    @Json(name = "total_results") val totalResults: Long,
    @Json(name = "total_pages") val totalPages: Int
) {

    val isFinalPage: Boolean
        get() = page >= totalPages

    companion object {

        fun <T> empty(): PagedResponse<T> =
            PagedResponse(
                page = 1,
                items = emptyList<T>(),
                totalResults = 0,
                totalPages = 1,
            )
    }
}
