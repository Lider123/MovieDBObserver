package ru.babaets.moviedbobserver.presentation.feature.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    viewModel {
        SearchViewModelImpl(
            searchMoviesUseCase = get(),
            getSearchKeywordsUseCase = get(),
            navigator = get(),
            stringProvider = get()
        )
    }

    factory<SearchMoviesUseCase> {
        SearchMoviesInteractor(
            api = get(),
            stringProvider = get()
        )
    }

    factory<GetSearchKeywordsUseCase> {
        GetSearchKeywordsInteractor(
            api = get(),
            stringProvider = get()
        )
    }
}
