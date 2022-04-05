package ru.babaets.moviedbobserver.presentation.feature.moviecard

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.utils.inflateBinding
import ru.babaets.moviedbobserver.common.utils.load
import ru.babaets.moviedbobserver.databinding.ViewItemVideoBinding
import ru.babaets.moviedbobserver.network.model.Video

class VideosAdapter(
    private val onItemClick: (Video) -> Unit
) : ListAdapter<Video, VideosAdapter.ViewHolder>(VideosDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(ViewItemVideoBinding::inflate), onItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ViewItemVideoBinding,
        onClick: (Video) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var item: Video? = null

        init {
            binding.cardThumbnail.setOnClickListener {
                item?.let(onClick)
            }
        }

        fun bind(item: Video) {
            this.item = item
            binding.run {
                tvName.text = item.name
                tvType.text = item.typeName
                item.thumbnailUrl?.let {
                    ivThumbnail.load(it, R.drawable.logo)
                } ?: ivThumbnail.load(R.drawable.logo)
            }
        }
    }

    private class VideosDiffUtilCallback : DiffUtil.ItemCallback<Video>() {

        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem == newItem
    }
}
