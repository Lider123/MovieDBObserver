package ru.babaets.moviedbobserver.presentation.feature.moviecard

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieCardModule = module {

    viewModel { (movieId: Long) ->
        MovieViewModelImpl(
            movieId = movieId,
            getMovieUseCase = get(),
            navigator = get()
        )
    }

    factory<GetMovieUseCase> {
        GetMovieInteractor(
            api = get(),
            stringProvider = get()
        )
    }
}
