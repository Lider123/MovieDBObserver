package ru.babaets.moviedbobserver.presentation.feature.feed

import androidx.lifecycle.LiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.IViewModel

interface FeedViewModel : IViewModel {

    val moviesLiveData: LiveData<PagingData<Movie>>

    fun onMoviePressed(movie: Movie)

    fun onLoadStateChanged(states: CombinedLoadStates)

    fun onRetryPressed()
}
