package ru.babaets.moviedbobserver.common.exception

class FetchPageException(
    override val message: String,
    override val cause: Throwable
) : Exception()
