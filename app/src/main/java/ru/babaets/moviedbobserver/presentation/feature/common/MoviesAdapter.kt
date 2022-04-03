package ru.babaets.moviedbobserver.presentation.feature.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.utils.load
import ru.babaets.moviedbobserver.common.utils.toFormattedString
import ru.babaets.moviedbobserver.common.utils.toUiString
import ru.babaets.moviedbobserver.databinding.ViewItemMovieBinding
import ru.babaets.moviedbobserver.network.model.Movie

class MoviesAdapter(
    private val onItemClick: (item: Movie) -> Unit
) : PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(MoviesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let {
                ViewHolder(it, onItemClick)
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    class ViewHolder(
        private val binding: ViewItemMovieBinding,
        private val onClick: (item: Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var movie: Movie? = null

        init {
            binding.cardPoster.setOnClickListener {
                movie?.let(onClick)
            }
        }

        fun bind(item: Movie) {
            movie = item
            binding.run {
                item.posterUrl?.let {
                    ivPoster.load(it, R.drawable.logo)
                } ?: ivPoster.load(R.drawable.logo)
                tvRating.text = item.averageVote.toFormattedString(1)
                tvTitle.text = item.title
                tvReleaseDate.text = item.releaseDate.toUiString()
            }
        }
    }

    private class MoviesDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}
