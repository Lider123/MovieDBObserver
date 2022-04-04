package ru.babaets.moviedbobserver.presentation.feature.home

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.databinding.FragmentHomeBinding
import ru.babaets.moviedbobserver.presentation.feature.common.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val layoutRes: Int = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModel()

    override fun populateProgress(isLoading: Boolean) = Unit

    override fun populateError(exception: Throwable?) = Unit

    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            viewPager.adapter = HomeTabsAdapter(this@HomeFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val homeTab = HomeTab.values()[position]
                tab.setIcon(homeTab.iconRes)
                tab.text = getString(homeTab.titleRes)
            }.attach()
        }
    }
}
