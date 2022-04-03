package ru.babaets.moviedbobserver.network.gateway

import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.utils.toApiString
import ru.babaets.moviedbobserver.network.Api
import ru.babaets.moviedbobserver.network.model.Movie
import java.util.*

class ApiGatewayImpl(
    private val api: Api
) : ApiGateway {

    override suspend fun getLatestMovies(): List<Movie> =
        api.discoverMovie(
            apiKey = BuildConfig.API_KEY,
            sortBy = "release_date.desc",
            releaseDateUntil = Date().toApiString()
        ).movies
}
