package ru.babaets.moviedbobserver.presentation.feature.search

import androidx.lifecycle.LiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.babaets.moviedbobserver.network.model.Keyword
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.IViewModel

interface SearchViewModel : IViewModel {

    val moviesLiveData: LiveData<PagingData<Movie>>

    val queryLiveData: LiveData<String>

    val keywordsLiveData: LiveData<List<Keyword>>

    fun onMoviePressed(movie: Movie)

    fun onLoadStateChanged(states: CombinedLoadStates)

    fun onKeywordPressed(keyword: Keyword)

    fun onUiQueryChanged(query: String)

    fun onSearchPressed()

    fun onRetryPressed()
}
