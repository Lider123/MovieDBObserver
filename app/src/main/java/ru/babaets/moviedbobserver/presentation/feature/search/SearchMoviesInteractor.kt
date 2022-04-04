package ru.babaets.moviedbobserver.presentation.feature.search

import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.common.exception.FetchDataException
import ru.babaets.moviedbobserver.network.Api
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse

class SearchMoviesInteractor(
    private val api: Api,
    private val stringProvider: StringProvider
) : SearchMoviesUseCase {

    override suspend fun execute(query: String, page: Int): PagedResponse<Movie> =
        try {
            if (query.isBlank()) {
                PagedResponse.empty()
            } else {
                api.searchMovie(
                    apiKey = BuildConfig.API_KEY,
                    query = query,
                    page = page
                )
            }
        } catch (e: Exception) {
            throw FetchDataException(stringProvider.GET_MOVIES_ERROR, e)
        }
}
