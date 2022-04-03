package ru.babaets.moviedbobserver.common.utils

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(@DrawableRes imageRes: Int) {
    Glide.with(this)
        .load(imageRes)
        .into(this)
}

fun ImageView.load(url: String, @DrawableRes placeholder: Int) {
    Glide.with(this)
        .load(Uri.parse(url))
        .apply(RequestOptions().override(0, 200))
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}
