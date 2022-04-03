package ru.babaets.moviedbobserver.presentation.feature.feed

import androidx.lifecycle.MutableLiveData
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewModel

class FeedViewModel(
    private val getLatestMoviesUseCase: GetLatestMoviesUseCase
) : BaseViewModel() {

    val latestMoviesLiveData = MutableLiveData<List<Movie>>()

    init {
        loadLatestMovies()
    }

    fun onRetryPressed() {
        loadLatestMovies()
    }

    fun onMoviePressed(movie: Movie) {
        // TODO: open movie screen
    }

    private fun loadLatestMovies() {
        launchWithLoading {
            latestMoviesLiveData.postValue(getLatestMoviesUseCase.execute())
        }
    }
}
