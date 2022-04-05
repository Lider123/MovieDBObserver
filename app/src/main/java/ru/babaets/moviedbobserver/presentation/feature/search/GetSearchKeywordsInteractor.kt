package ru.babaets.moviedbobserver.presentation.feature.search

import ru.babaets.moviedbobserver.BuildConfig
import ru.babaets.moviedbobserver.common.StringProvider
import ru.babaets.moviedbobserver.common.exception.FetchDataException
import ru.babaets.moviedbobserver.network.Api
import ru.babaets.moviedbobserver.network.model.Keyword

class GetSearchKeywordsInteractor(
    private val api: Api,
    private val stringProvider: StringProvider
) : GetSearchKeywordsUseCase {

    override suspend fun execute(query: String): List<Keyword> =
        try {
            if (query.isBlank()) {
                emptyList()
            } else {
                api.searchKeyword(
                    apiKey = BuildConfig.API_KEY,
                    query = query,
                    page = 1
                ).items.take(KEYWORD_MAX_COUNT)
            }
        } catch (e: Exception) {
            throw FetchDataException(stringProvider.GET_MOVIES_ERROR, e,
                tag = GetSearchKeywordsUseCase.TAG
            )
        }

    companion object {
        private const val KEYWORD_MAX_COUNT = 6
    }
}