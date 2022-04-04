package ru.babaets.moviedbobserver.presentation.feature.search

import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse

interface SearchMoviesUseCase {

    suspend fun execute(query: String, page: Int): PagedResponse<Movie>
}
