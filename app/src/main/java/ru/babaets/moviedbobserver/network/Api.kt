package ru.babaets.moviedbobserver.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.babaets.moviedbobserver.network.model.Keyword
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

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String
    ): Movie

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): PagedResponse<Movie>

    @GET("search/keyword")
    suspend fun searchKeyword(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): PagedResponse<Keyword>
}
