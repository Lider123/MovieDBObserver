package ru.babaets.moviedbobserver.presentation.feature.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    protected var item: T? = null
        private set

    open fun bind(item: T) {
        this.item = item
    }
}
