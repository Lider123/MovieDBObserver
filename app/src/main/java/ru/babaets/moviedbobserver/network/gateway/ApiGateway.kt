package ru.babaets.moviedbobserver.network.gateway

import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse

interface ApiGateway {

    suspend fun getLatestMovies(page: Int): PagedResponse<Movie>
}
