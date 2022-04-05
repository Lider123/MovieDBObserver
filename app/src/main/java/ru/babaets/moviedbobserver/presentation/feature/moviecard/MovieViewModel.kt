package ru.babaets.moviedbobserver.presentation.feature.moviecard

import androidx.lifecycle.LiveData
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.IViewModel

interface MovieViewModel : IViewModel {

    val movieLiveData: LiveData<Movie>

    fun onRetryPressed()
}
