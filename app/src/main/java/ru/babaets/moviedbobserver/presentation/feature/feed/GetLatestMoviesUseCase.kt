package ru.babaets.moviedbobserver.presentation.feature.feed

import ru.babaets.moviedbobserver.network.model.Movie

interface GetLatestMoviesUseCase {

    suspend fun execute(): List<Movie>
}
