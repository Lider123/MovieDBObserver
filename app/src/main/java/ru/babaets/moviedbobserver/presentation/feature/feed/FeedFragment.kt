package ru.babaets.moviedbobserver.presentation.feature.feed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.databinding.FragmentFeedBinding
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseFragment
import ru.babaets.moviedbobserver.presentation.feature.common.MoviesAdapter

class FeedFragment : BaseFragment<FeedViewModel>() {

    override val layoutRes: Int = R.layout.fragment_feed

    override val viewModel: FeedViewModel by viewModel<FeedViewModelImpl>()

    private val binding: FragmentFeedBinding by viewBinding()

    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(viewModel::onMoviePressed).apply {
            addLoadStateListener(viewModel::onLoadStateChanged)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.adapter = adapter

        viewModel.moviesLiveData.observe(viewLifecycleOwner, ::populateMovies)
    }

    override fun populateProgress(isLoading: Boolean) {
        binding.progress.isVisible = isLoading
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

    private fun populateMovies(movies: PagingData<Movie>) {
        adapter.submitData(lifecycle, movies)
    }
}
