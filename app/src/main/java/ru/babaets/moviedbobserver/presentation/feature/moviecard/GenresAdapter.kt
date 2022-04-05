package ru.babaets.moviedbobserver.presentation.feature.moviecard

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.babaets.moviedbobserver.common.utils.inflateBinding
import ru.babaets.moviedbobserver.databinding.ViewItemGenreBinding
import ru.babaets.moviedbobserver.network.model.Genre
import ru.babaets.moviedbobserver.presentation.feature.common.BaseAdapter
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewHolder

class GenresAdapter : BaseAdapter<Genre>(GenresDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(ViewItemGenreBinding::inflate))

    class ViewHolder(
        private val binding: ViewItemGenreBinding
    ) : BaseViewHolder<Genre>(binding.root) {

        override fun bind(item: Genre) {
            super.bind(item)
            binding.root.text = item.name
        }
    }

    class GenresDiffUtilCallback : DiffUtil.ItemCallback<Genre>() {

        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem == newItem
    }
}
