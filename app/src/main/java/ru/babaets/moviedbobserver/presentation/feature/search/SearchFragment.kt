package ru.babaets.moviedbobserver.presentation.feature.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.utils.hideKeyboard
import ru.babaets.moviedbobserver.databinding.FragmentSearchBinding
import ru.babaets.moviedbobserver.network.model.Keyword
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseFragment
import ru.babaets.moviedbobserver.presentation.feature.common.MoviesAdapter

class SearchFragment : BaseFragment<SearchViewModel>() {

    override val layoutRes: Int = R.layout.fragment_search

    override val viewModel: SearchViewModel by viewModel()

    private val binding: FragmentSearchBinding by viewBinding()

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(viewModel::onMoviePressed).apply {
            addLoadStateListener(viewModel::onLoadStateChanged)
        }
    }

    private val keywordsAdapter: KeywordsAdapter by lazy {
        KeywordsAdapter(viewModel::onKeywordPressed)
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
            rvMovies.adapter = moviesAdapter
            rvKeywords.run {
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent =JustifyContent.FLEX_START
                    flexWrap = FlexWrap.WRAP
                }
                adapter = keywordsAdapter
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.moviesFlow.collect(::populateMovies)
        }
        lifecycleScope.launchWhenResumed {
            viewModel.queryFlow.collect(::populateQuery)
        }
        viewModel.keywordsLiveData.observe(viewLifecycleOwner, ::populateKeywords)
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
        moviesAdapter.submitData(movies)
    }

    private suspend fun populateQuery(query: String) {
        binding.inputSearch.run {
            if (query == text.toString()) return

            setText(query)
            setSelection(query.length)
        }
    }

    private fun populateKeywords(keywords: List<Keyword>) {
        keywordsAdapter.submitList(keywords) {
            binding.rvKeywords.isVisible = keywords.isNotEmpty()
        }
    }
}
