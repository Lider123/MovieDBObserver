package ru.babaets.moviedbobserver.network.gateway

import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.utils.toApiString
import ru.babaets.moviedbobserver.network.Api
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse
import java.util.*

class ApiGatewayImpl(
    private val api: Api
) : ApiGateway {

    override suspend fun getLatestMovies(page: Int): PagedResponse<Movie> =
        api.discoverMovie(
            apiKey = BuildConfig.API_KEY,
            page = page,
            sortBy = "release_date.desc",
            releaseDateUntil = Date().toApiString()
        )
}
