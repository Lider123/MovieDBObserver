package ru.babaets.moviedbobserver.network.model

import com.squareup.moshi.Json

data class Video(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "key") val key: String,
    @Json(name = "site") val siteName: String,
    @Json(name = "type") val typeName: String
) {

    val videoUrl: String?
        get() = site?.videoUrlFactory?.invoke(key)

    val thumbnailUrl: String?
        get() = site?.thumbnailUrlFactory?.invoke(key)

    val site: Site?
        get() = Site.findByApiName(siteName)

    enum class Site(
        private val apiName: String,
        val videoUrlFactory: (key: String) -> String,
        val thumbnailUrlFactory: (key: String) -> String,
    ) {
        YOUTUBE(
            apiName = "YouTube",
            videoUrlFactory = { key -> "https://www.youtube.com/watch?v=$key" },
            thumbnailUrlFactory = { key -> "https://img.youtube.com/vi/$key/hqdefault.jpg" }
        );

        companion object {

            fun findByApiName(apiName: String): Site? = values().find { it.apiName == apiName }
        }
    }
}
