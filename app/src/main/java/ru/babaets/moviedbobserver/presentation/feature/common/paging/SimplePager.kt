package ru.babaets.moviedbobserver.presentation.feature.common.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.babaets.moviedbobserver.common.exception.EmptyDataException
import ru.babaets.moviedbobserver.network.model.PagedResponse

class SimplePager<T : Any, R : PagedResponse<T>>(
    private val loadNext: suspend (page: Int) -> R,
    private val exceptionProvider: PagingExceptionProvider
) {
    private val pagingSource: PagingSource<Int, T>
        get() = object : PagingSource<Int, T>() {

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                val currentPage = params.key ?: START_PAGE
                return try {
                    val response = loadNext.invoke(currentPage)
                    if (response.items.isEmpty() && currentPage == START_PAGE) throw exceptionProvider.emptyError

                    LoadResult.Page(
                        data = response.items,
                        prevKey = if (currentPage == START_PAGE) null else currentPage - 1,
                        nextKey = if (response.isFinalPage) null else currentPage + 1
                    )
                } catch (e: Exception) {
                    val exception = if (e is EmptyDataException) e else exceptionProvider.getPageError(e)
                    LoadResult.Error(exception)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, T>): Int? =
                state.anchorPosition?.let {
                    state.closestPageToPosition(it)
                        ?.prevKey
                        ?.plus(1)
                        ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
                }
        }
    private val pagingSourceFactory = object : InvalidatingPagingSourceFactory<Int, T>() {

        override fun create(): PagingSource<Int, T> = pagingSource
    }
    private val pager = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        pagingSourceFactory = pagingSourceFactory
    )

    val flow = pager.flow

    fun invalidate() = pagingSourceFactory.invalidate()

    companion object {
        private const val START_PAGE = 1
    }
}
