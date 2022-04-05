package ru.babaets.moviedbobserver.presentation.feature.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.babaets.moviedbobserver.common.utils.inflateBinding
import ru.babaets.moviedbobserver.databinding.ViewItemKeywordBinding
import ru.babaets.moviedbobserver.network.model.Keyword

class KeywordsAdapter(
    private val onItemClick: (Keyword) -> Unit
) : ListAdapter<Keyword, KeywordsAdapter.ViewHolder>(KeywordsDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(ViewItemKeywordBinding::inflate), onItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ViewItemKeywordBinding,
        onClick: (Keyword) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var item: Keyword? = null

        init {
            binding.root.setOnClickListener {
                item?.let(onClick)
            }
        }

        fun bind(item: Keyword) {
            this.item = item
            binding.tvName.text = item.name
        }
    }

    private class KeywordsDiffUtilCallback : DiffUtil.ItemCallback<Keyword>() {

        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean =
            oldItem == newItem
    }
}
