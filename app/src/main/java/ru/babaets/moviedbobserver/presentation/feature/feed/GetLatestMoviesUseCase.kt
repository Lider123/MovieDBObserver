package ru.babaets.moviedbobserver.presentation.feature.feed

import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse

interface GetLatestMoviesUseCase {

    suspend fun execute(page: Int): PagedResponse<Movie>
}
