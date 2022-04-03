package ru.babaets.moviedbobserver.network.gateway

import ru.babaets.moviedbobserver.network.model.Movie

interface ApiGateway {

    suspend fun getLatestMovies(): List<Movie>
}
