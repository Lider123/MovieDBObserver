package ru.babaets.moviedbobserver.common.utils

import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

fun Fragment.hideKeyboard() {
    ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        ?.hideSoftInputFromWindow(requireView().windowToken, 0)
}
