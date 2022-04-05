package ru.babaets.moviedbobserver.presentation.feature.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.common.exception.EmptyDataException
import ru.babaets.moviedbobserver.common.exception.FetchPageException
import ru.babaets.moviedbobserver.common.navigation.AppNavigator
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewModel
import ru.babaets.moviedbobserver.presentation.feature.common.paging.PagingExceptionProvider
import ru.babaets.moviedbobserver.presentation.feature.common.paging.SimplePager
import ru.babaets.moviedbobserver.presentation.feature.home.HomeFragmentDirections

class FeedViewModelImpl(
    private val getLatestMoviesUseCase: GetLatestMoviesUseCase,
    private val stringProvider: StringProvider,
    navigator: AppNavigator
) : BaseViewModel(navigator), FeedViewModel {

    override val moviesLiveData: LiveData<PagingData<Movie>>
        get() = moviesFlow.asLiveData(coroutineContext)

    private val pagingExceptionProvider = object : PagingExceptionProvider {

        override val emptyError: EmptyDataException
            get() = EmptyDataException(stringProvider.EMPTY_MOVIES_ERROR)

        override fun getPageError(cause: Exception): FetchPageException =
            FetchPageException(stringProvider.GET_MOVIES_ERROR, cause)
    }

    private val moviesPager = SimplePager(::loadNext, pagingExceptionProvider)

    private val moviesFlow = moviesPager.flow.cachedIn(viewModelScope)

    override fun onRetryPressed() {
        moviesPager.invalidate()
    }

    override fun onMoviePressed(movie: Movie) {
        navigator.forward(HomeFragmentDirections.toMovie(movie.id))
    }

    override fun onLoadStateChanged(states: CombinedLoadStates) {
        when (val state = states.refresh) {
            is LoadState.NotLoading -> {
                progressLiveData.postValue(false)
                errorLiveData.postValue(null)
            }
            LoadState.Loading -> {
                progressLiveData.postValue(true)
                errorLiveData.postValue(null)
            }
            is LoadState.Error -> {
                progressLiveData.postValue(false)
                onError(coroutineContext, state.error)
            }
        }
    }

    private suspend fun loadNext(page: Int): PagedResponse<Movie> =
        getLatestMoviesUseCase.execute(page)
}
