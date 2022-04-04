package ru.babaets.moviedbobserver.presentation.feature.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HomeTab.values().size

    override fun createFragment(position: Int): Fragment =
        HomeTab.values()[position].fragmentFactory.invoke()
}
