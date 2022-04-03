package ru.babaets.moviedbobserver.presentation.feature.moviecard

import androidx.lifecycle.MutableLiveData
import ru.babaets.moviedbobserver.common.navigation.AppNavigator
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewModel

class MovieViewModel(
    private val movieId: Long,
    private val getMovieUseCase: GetMovieUseCase,
    private val navigator: AppNavigator
) : BaseViewModel() {

    val movieLiveData = MutableLiveData<Movie>()

    init {
        loadMovie()
    }

    fun onRetryPressed() {
        loadMovie()
    }

    fun onBackPressed() {
        navigator.back()
    }

    private fun loadMovie() {
        launchWithLoading {
            movieLiveData.postValue(getMovieUseCase.execute(movieId))
        }
    }
}
