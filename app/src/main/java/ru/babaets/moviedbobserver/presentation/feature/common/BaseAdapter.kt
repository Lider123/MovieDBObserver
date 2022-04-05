package ru.babaets.moviedbobserver.presentation.feature.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T>(
    diffUtilCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<T>>(diffUtilCallback) {

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }
}
