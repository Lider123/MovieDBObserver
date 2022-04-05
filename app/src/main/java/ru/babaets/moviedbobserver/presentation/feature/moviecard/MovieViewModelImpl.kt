package ru.babaets.moviedbobserver.presentation.feature.moviecard

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import ru.babaets.moviedbobserver.common.externalaction.ExternalActionHandler
import ru.babaets.moviedbobserver.common.navigation.AppNavigator
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.Video
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewModel

class MovieViewModelImpl(
    private val movieId: Long,
    private val getMovieUseCase: GetMovieUseCase,
    private val externalActionHandler: ExternalActionHandler,
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

    override fun onVideoPressed(video: Video) {
        video.videoUrl?.let(externalActionHandler::handleOuterLink)
    }

    private fun loadMovie() {
        launchWithLoading(Dispatchers.IO) {
            movieLiveData.postValue(getMovieUseCase.execute(movieId))
        }
    }
}
