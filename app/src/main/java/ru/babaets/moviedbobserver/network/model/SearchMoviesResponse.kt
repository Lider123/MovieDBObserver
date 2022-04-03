package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json

data class SearchMoviesResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val movies: List<Movie>,
    @Json(name = "total_results") val totalResults: Long,
    @Json(name = "total_pages") val totalPages: Int
)
