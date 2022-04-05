package ru.babaets.moviedbobserver.presentation.feature.feed

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {

    viewModel {
        FeedViewModelImpl(
            getLatestMoviesUseCase = get(),
            stringProvider = get(),
            navigator = get()
        )
    }

    factory<GetLatestMoviesUseCase> {
        GetLatestMoviesInteractor(
            api = get(),
            stringProvider = get()
        )
    }
}
