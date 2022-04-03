package ru.babaets.moviedbobserver.presentation.feature.common.paging

import ru.babaets.moviedbobserver.common.exception.EmptyDataException
import ru.babaets.moviedbobserver.common.exception.FetchPageException

interface PagingExceptionProvider {
    val emptyError: EmptyDataException

    fun getPageError(cause: Exception): FetchPageException
}
