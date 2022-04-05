package ru.babaets.moviedbobserver.presentation.feature.moviecard

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.utils.inflateBinding
import ru.babaets.moviedbobserver.common.utils.load
import ru.babaets.moviedbobserver.databinding.ViewItemVideoBinding
import ru.babaets.moviedbobserver.network.model.Video
import ru.babaets.moviedbobserver.presentation.feature.common.BaseAdapter
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewHolder

class VideosAdapter(
    private val onItemClick: (Video) -> Unit
) : BaseAdapter<Video>(VideosDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(ViewItemVideoBinding::inflate), onItemClick)

    class ViewHolder(
        private val binding: ViewItemVideoBinding,
        onClick: (Video) -> Unit
    ) : BaseViewHolder<Video>(binding.root) {

        init {
            binding.cardThumbnail.setOnClickListener {
                item?.let(onClick)
            }
        }

        override fun bind(item: Video) {
            super.bind(item)
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
