package ru.babaets.moviedbobserver.common

import android.content.res.Resources
import ru.babaets.moviedbobserver.R

class StringProviderImpl(resources: Resources) : StringProvider {
    override val GET_MOVIES_ERROR: String = resources.getString(R.string.get_movies_error)
    override val EMPTY_MOVIES_ERROR: String = resources.getString(R.string.empty_movies_error)
}
