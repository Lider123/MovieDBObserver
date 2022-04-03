package ru.babaets.moviedbobserver.common.utils

import java.text.SimpleDateFormat
import java.util.*

const val API_DATE_FORMAT = "yyyy-MM-dd"
const val UI_DATE_FORMAT = "d MMM yyyy"

fun Date.toApiString(): String =
    SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).format(this)

fun Date.toUiString(): String =
    SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault()).format(this)

fun String.parseApiDate(): Date =
    SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault()).parse(this)!!
