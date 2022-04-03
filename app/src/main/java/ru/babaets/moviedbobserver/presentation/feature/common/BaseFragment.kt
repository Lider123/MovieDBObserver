package ru.babaets.moviedbobserver.presentation.feature.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val layoutRes: Int

    abstract val viewModel: VM

    abstract fun populateProgress(isLoading: Boolean)

    abstract fun populateError(exception: Throwable?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressLiveData.observe(viewLifecycleOwner, ::populateProgress)
        viewModel.errorLiveData.observe(viewLifecycleOwner, ::populateError)
    }
}
