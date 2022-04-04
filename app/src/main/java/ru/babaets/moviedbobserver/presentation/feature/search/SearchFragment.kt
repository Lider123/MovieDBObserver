package ru.babaets.moviedbobserver.presentation.feature.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.utils.hideKeyboard
import ru.babaets.moviedbobserver.databinding.FragmentSearchBinding
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseFragment
import ru.babaets.moviedbobserver.presentation.feature.common.MoviesAdapter

class SearchFragment : BaseFragment<SearchViewModel>() {

    override val layoutRes: Int = R.layout.fragment_search

    override val viewModel: SearchViewModel by viewModel()

    private val binding: FragmentSearchBinding by viewBinding()

    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(viewModel::onMoviePressed).apply {
            addLoadStateListener(viewModel::onLoadStateChanged)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            inputSearch.run {
                doAfterTextChanged {
                    btnClear.isVisible = it.toString().isNotEmpty()
                    viewModel.onUiQueryChanged(it.toString())
                }
                setOnEditorActionListener { _, i, _ ->
                    when (i) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            viewModel.onSearchPressed()
                            hideKeyboard()
                            true
                        }
                        else -> false
                    }
                }
            }
            btnClear.setOnClickListener {
                inputSearch.setText("")
            }
            btnSearch.setOnClickListener {
                viewModel.onSearchPressed()
                hideKeyboard()
            }
            rvMovies.adapter = adapter
        }

        lifecycleScope.launchWhenResumed {
            viewModel.moviesFlow.collect(::populateMovies)
        }
    }

    override fun populateError(exception: Throwable?) {
        binding.layoutError.root.isVisible = exception != null
        exception ?: return

        binding.layoutError.run {
            tvErrorMessage.text = exception.message
            btnErrorAction.run {
                setText(R.string.retry)
                setOnClickListener {
                    viewModel.onRetryPressed()
                }
            }
        }
    }

    override fun populateProgress(isLoading: Boolean) {
        binding.progress.isVisible = isLoading
    }

    private suspend fun populateMovies(movies: PagingData<Movie>) {
        adapter.submitData(movies)
        binding.rvMovies.isVisible = adapter.itemCount > 0
    }
}
