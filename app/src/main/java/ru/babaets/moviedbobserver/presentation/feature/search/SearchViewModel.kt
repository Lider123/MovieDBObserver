package ru.babaets.moviedbobserver.presentation.feature.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.common.exception.EmptyDataException
import ru.babaets.moviedbobserver.common.exception.FetchDataException
import ru.babaets.moviedbobserver.common.exception.FetchPageException
import ru.babaets.moviedbobserver.common.navigation.AppNavigator
import ru.babaets.moviedbobserver.network.model.Keyword
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.network.model.PagedResponse
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewModel
import ru.babaets.moviedbobserver.presentation.feature.common.paging.PagingExceptionProvider
import ru.babaets.moviedbobserver.presentation.feature.common.paging.SimplePager
import ru.babaets.moviedbobserver.presentation.feature.home.HomeFragmentDirections
import kotlin.coroutines.CoroutineContext

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getSearchKeywordsUseCase: GetSearchKeywordsUseCase,
    private val navigator: AppNavigator,
    stringProvider: StringProvider
) : BaseViewModel() {

    private val pagingExceptionProvider = object : PagingExceptionProvider {

        override val emptyError: EmptyDataException
            get() = EmptyDataException(stringProvider.EMPTY_MOVIES_SEARCH_ERROR)

        override fun getPageError(cause: Exception): FetchPageException =
            FetchPageException(stringProvider.GET_MOVIES_ERROR, cause)
    }

    private val moviesPager = SimplePager(::loadNext, pagingExceptionProvider)

    val moviesFlow = moviesPager.flow.cachedIn(viewModelScope)

    val queryFlow: MutableStateFlow<String> = MutableStateFlow("")

    val keywordsLiveData = MutableLiveData<List<Keyword>>()

    init {
        queryFlow.debounce(QUERY_UPDATES_DEBOUNCE)
            .onEach(::onQueryChanged)
            .launchIn(this)
    }

    override fun onError(context: CoroutineContext, error: Throwable) {
        if (error is FetchDataException && error.tag == GetSearchKeywordsUseCase.TAG) return

        super.onError(context, error)
    }

    fun onKeywordPressed(keyword: Keyword) {
        keywordsLiveData.postValue(emptyList())
        queryFlow.value = keyword.name
        moviesPager.invalidate()
    }

    fun onSearchPressed() {
        keywordsLiveData.postValue(emptyList())
        moviesPager.invalidate()
    }

    fun onUiQueryChanged(query: String) {
        queryFlow.value = query
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

    private fun onQueryChanged(query: String) {
        launch {
            keywordsLiveData.postValue(getSearchKeywordsUseCase.execute(query))
        }
    }

    private suspend fun loadNext(page: Int): PagedResponse<Movie> =
        searchMoviesUseCase.execute(queryFlow.value, page)

    companion object {
        private const val QUERY_UPDATES_DEBOUNCE = 500L
    }
}
