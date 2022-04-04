package ru.babaets.moviedbobserver.presentation.feature.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.presentation.feature.feed.FeedFragment
import ru.babaets.moviedbobserver.presentation.feature.search.SearchFragment

enum class HomeTab(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val fragmentFactory: () -> Fragment
) {
    FEED(R.string.home_tab_feed_title, R.drawable.ic_home, ::FeedFragment),
    SEARCH(R.string.home_tab_search_title, R.drawable.ic_search, ::SearchFragment)
}