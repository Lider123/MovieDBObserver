package ru.babaets.moviedbobserver.presentation.feature.moviecard

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import ru.babaets.moviedbobserver.common.navigation.AppNavigator
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewModel

class MovieViewModelImpl(
    private val movieId: Long,
    private val getMovieUseCase: GetMovieUseCase,
    navigator: AppNavigator
) : BaseViewModel(navigator), MovieViewModel {

    override val movieLiveData = MutableLiveData<Movie>()

    init {
        loadMovie()
    }

    override fun onRetryPressed() {
        loadMovie()
    }

    override fun onBackPressed() {
        navigator.back()
    }

    private fun loadMovie() {
        launchWithLoading(Dispatchers.IO) {
            movieLiveData.postValue(getMovieUseCase.execute(movieId))
        }
    }
}
