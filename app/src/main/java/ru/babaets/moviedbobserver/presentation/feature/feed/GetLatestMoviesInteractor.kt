package ru.babaets.moviedbobserver.presentation.feature.feed

import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.network.gateway.ApiGateway
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.FetchDataException

class GetLatestMoviesInteractor(
    private val gateway: ApiGateway,
    private val stringProvider: StringProvider
) : GetLatestMoviesUseCase {

    override suspend fun execute(): List<Movie> =
        try {
            gateway.getLatestMovies()
        } catch (e: Exception) {
            throw FetchDataException(stringProvider.GET_MOVIES_ERROR, e)
        }
}
