package ru.babaets.moviedbobserver.presentation.feature.moviecard

import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.common.exception.FetchDataException
import ru.babaets.moviedbobserver.network.Api
import ru.babaets.moviedbobserver.network.model.Movie

class GetMovieInteractor(
    private val api: Api,
    private val stringProvider: StringProvider
) : GetMovieUseCase {

    override suspend fun execute(movieId: Long): Movie =
        try {
            api.getMovie(
                apiKey = BuildConfig.API_KEY,
                movieId = movieId,
                appendToResponse = "videos"
            )
        } catch (e: Exception) {
            throw FetchDataException(stringProvider.GET_MOVIE_ERROR, e)
        }
}
