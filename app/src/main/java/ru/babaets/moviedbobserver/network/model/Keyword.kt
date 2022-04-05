package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json

data class Keyword(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
)
