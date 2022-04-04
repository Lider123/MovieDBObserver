package ru.babaets.moviedbobserver.presentation.feature.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel {
        HomeViewModel()
    }
}
