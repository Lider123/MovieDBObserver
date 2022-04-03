package ru.babaets.moviedbobserver.presentation.feature.common

class FetchDataException(
    override val message: String,
    override val cause: Throwable
) : Exception()
