package ru.babaets.moviedbobserver.presentation.feature.moviecard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.utils.load
import ru.babaets.moviedbobserver.databinding.FragmentMovieBinding
import ru.babaets.moviedbobserver.network.model.Movie
import ru.babaets.moviedbobserver.presentation.feature.common.BaseFragment

class MovieFragment : BaseFragment<MovieViewModel>() {

    override val layoutRes: Int = R.layout.fragment_movie

    override val viewModel: MovieViewModel by viewModel<MovieViewModelImpl> {
        parametersOf(args.movieId)
    }

    private val binding: FragmentMovieBinding by viewBinding()

    private val args: MovieFragmentArgs by navArgs()

    private val genresAdapter: GenresAdapter by lazy {
        GenresAdapter()
    }

    private val videosAdapter: VideosAdapter by lazy {
        VideosAdapter(viewModel::onVideoPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            toolbar.setNavigationOnClickListener {
                viewModel.onBackPressed()
            }
            rvGenres.adapter = genresAdapter
            rvVideos.adapter = videosAdapter
        }

        viewModel.movieLiveData.observe(viewLifecycleOwner, ::populateMovie)
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

    private fun populateMovie(movie: Movie) {
        binding.run {
            ivPoster.run {
                movie.posterUrl?.let {
                    load(it, R.drawable.logo)
                }
                isVisible = movie.posterUrl != null
            }
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            ratingBar.rating = movie.averageVote / 2
            tvRating.text = resources.getString(R.string.tmdb_rating_placeholder, movie.averageVote)
            genresAdapter.submitList(movie.genres) {
                rvGenres.isVisible = !movie.genres.isNullOrEmpty()
            }
            tvTimeInfo.text = movie.formattedDuration?.let {
                "${movie.releaseYearString} | $it"
            } ?: movie.releaseYearString
            videosAdapter.submitList(movie.videos?.results) {
                groupVideos.isVisible = !movie.videos?.results.isNullOrEmpty()
            }
        }
    }
}
