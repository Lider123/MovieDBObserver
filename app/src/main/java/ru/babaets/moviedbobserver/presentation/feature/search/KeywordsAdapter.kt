package ru.babaets.moviedbobserver.presentation.feature.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.babaets.moviedbobserver.common.utils.inflateBinding
import ru.babaets.moviedbobserver.databinding.ViewItemKeywordBinding
import ru.babaets.moviedbobserver.network.model.Keyword
import ru.babaets.moviedbobserver.presentation.feature.common.BaseAdapter
import ru.babaets.moviedbobserver.presentation.feature.common.BaseViewHolder

class KeywordsAdapter(
    private val onItemClick: (Keyword) -> Unit
) : BaseAdapter<Keyword>(KeywordsDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(ViewItemKeywordBinding::inflate), onItemClick)

    class ViewHolder(
        private val binding: ViewItemKeywordBinding,
        onClick: (Keyword) -> Unit
    ) : BaseViewHolder<Keyword>(binding.root) {

        init {
            binding.root.setOnClickListener {
                item?.let(onClick)
            }
        }

        override fun bind(item: Keyword) {
            super.bind(item)
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
