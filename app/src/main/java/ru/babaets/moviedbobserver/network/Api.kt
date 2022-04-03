package ru.babaets.moviedbobserver.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse

interface Api {

    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("release_date.lte") releaseDateUntil: String
    ): PagedResponse<Movie>
}
