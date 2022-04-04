package ru.babaets.moviedbobserver.presentation.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
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

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val navigator: AppNavigator,
    stringProvider: StringProvider
) : BaseViewModel() {

    private var query: String = ""

    private val pagingExceptionProvider = object : PagingExceptionProvider {

        override val emptyError: EmptyDataException
            get() = EmptyDataException(stringProvider.EMPTY_MOVIES_SEARCH_ERROR)

        override fun getPageError(cause: Exception): FetchPageException =
            FetchPageException(stringProvider.GET_MOVIES_ERROR, cause)
    }

    private val moviesPager = SimplePager(::loadNext, pagingExceptionProvider)

    val moviesFlow = moviesPager.flow.cachedIn(viewModelScope)

    fun onSearchPressed() {
        moviesPager.invalidate()
    }

    fun onUiQueryChanged(query: String) {
        this.query = query
    }

    fun onRetryPressed() {
        moviesPager.invalidate()
    }

    fun onMoviePressed(movie: Movie) {
        navigator.forward(HomeFragmentDirections.toMovie(movie.id))
    }

    fun onLoadStateChanged(states: CombinedLoadStates) {
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
        searchMoviesUseCase.execute(query, page)
}
