package ru.babaets.moviedbobserver.common.externalaction

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.annotation.StringRes

class ExternalIntentHandler(
    private val context: Context
) : ExternalActionHandler {

    override fun handleOuterLink(link: String): Boolean =
        Intent(ACTION_VIEW).apply {
            data = Uri.parse(link)
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }.let {
            context.startActivitySafely(it)
        }

    override fun handleOuterLink(@StringRes linkRes: Int): Boolean =
        handleOuterLink(context.getString(linkRes))

    private fun Context.startActivitySafely(intent: Intent): Boolean =
        try {
            startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
}
