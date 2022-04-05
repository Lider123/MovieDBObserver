package ru.babaets.moviedbobserver.common.externalaction

import androidx.annotation.StringRes

interface ExternalActionHandler {

    fun handleOuterLink(link: String): Boolean

    fun handleOuterLink(@StringRes linkRes: Int): Boolean
}
