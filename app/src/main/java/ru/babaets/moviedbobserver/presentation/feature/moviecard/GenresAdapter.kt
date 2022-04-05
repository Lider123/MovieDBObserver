package ru.babaets.moviedbobserver.presentation.feature.moviecard

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.babaets.moviedbobserver.common.utils.inflateBinding
import ru.babaets.moviedbobserver.databinding.ViewItemGenreBinding
import ru.babaets.moviedbobserver.network.model.Genre

class GenresAdapter : ListAdapter<Genre, GenresAdapter.ViewHolder>(GenresDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(ViewItemGenreBinding::inflate))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ViewItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Genre) {
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
