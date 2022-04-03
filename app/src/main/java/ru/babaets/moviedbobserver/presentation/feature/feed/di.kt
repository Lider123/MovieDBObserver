package ru.babaets.moviedbobserver.presentation.feature.feed

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {

    viewModel {
        FeedViewModel(
            getLatestMoviesUseCase = get(),
            stringProvider = get()
        )
    }

    factory<GetLatestMoviesUseCase> {
        GetLatestMoviesInteractor(
            gateway = get(),
            stringProvider = get()
        )
    }
}
