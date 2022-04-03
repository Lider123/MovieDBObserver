package ru.babaets.moviedbobserver.presentation.feature.moviecard

import ru.babaets.moviedbobserver.network.model.Movie

interface GetMovieUseCase {

    suspend fun execute(movieId: Long): Movie
}
