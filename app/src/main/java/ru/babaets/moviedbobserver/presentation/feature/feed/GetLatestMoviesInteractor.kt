package ru.babaets.moviedbobserver.presentation.feature.feed

import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.network.gateway.ApiGateway
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.common.exception.FetchDataException
import ru.babaets.moviedbobserver.network.model.PagedResponse

class GetLatestMoviesInteractor(
    private val gateway: ApiGateway,
    private val stringProvider: StringProvider
) : GetLatestMoviesUseCase {

    override suspend fun execute(page: Int): PagedResponse<Movie> =
        try {
            gateway.getLatestMovies(page)
        } catch (e: Exception) {
            throw FetchDataException(stringProvider.GET_MOVIES_ERROR, e)
        }
}
