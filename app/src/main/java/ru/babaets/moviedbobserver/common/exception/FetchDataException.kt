package ru.babaets.moviedbobserver.common.exception

class FetchDataException(
    override val message: String,
    override val cause: Throwable,
    val tag: Any? = null
) : Exception()
