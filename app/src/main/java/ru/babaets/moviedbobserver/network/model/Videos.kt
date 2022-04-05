package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json

data class Videos(
    @Json(name = "results") val results: List<Video>
)
