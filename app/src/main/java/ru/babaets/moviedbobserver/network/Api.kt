package ru.babaets.moviedbobserver.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.babaets.moviedbobserver.network.model.SearchMoviesResponse

interface Api {

    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("release_date.lte") releaseDateUntil: String
    ): SearchMoviesResponse
}
