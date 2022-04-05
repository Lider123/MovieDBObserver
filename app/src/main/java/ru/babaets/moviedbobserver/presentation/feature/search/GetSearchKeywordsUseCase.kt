package ru.babaets.moviedbobserver.presentation.feature.search

import ru.babaets.moviedbobserver.network.model.Keyword

interface GetSearchKeywordsUseCase {

    suspend fun execute(query: String): List<Keyword>

    companion object {
        val TAG = GetSearchKeywordsUseCase::class.simpleName
    }
}
