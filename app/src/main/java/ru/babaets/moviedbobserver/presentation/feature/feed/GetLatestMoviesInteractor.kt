package ru.babaets.moviedbobserver.presentation.feature.feed

import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.common.exception.FetchDataException
import ru.babaets.moviedbobserver.common.utils.toApiString
import ru.babaets.moviedbobserver.network.Api
import ru.babaets.moviedbobserver.network.model.PagedResponse
import java.util.*

class GetLatestMoviesInteractor(
    private val api: Api,
    private val stringProvider: StringProvider
) : GetLatestMoviesUseCase {

    override suspend fun execute(page: Int): PagedResponse<Movie> =
        try {
            api.discoverMovie(
                apiKey = BuildConfig.API_KEY,
                page = page,
                sortBy = "release_date.desc",
                releaseDateUntil = Date().toApiString()
            )
        } catch (e: Exception) {
            throw FetchDataException(stringProvider.GET_MOVIES_ERROR, e)
        }
}
