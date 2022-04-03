package ru.babaets.moviedbobserver.common.utils

fun Float.toFormattedString(trailingNumbersCount: Int): String {
    if (trailingNumbersCount < 0) throw IllegalArgumentException("Trailing numbers count can't be less then 0")

    return String.format("%.${trailingNumbersCount}f", this)
}
