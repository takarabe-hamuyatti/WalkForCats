package com.hamu.walkforcats.utils

import android.app.AlertDialog
import android.content.Context

fun confirmDialog(
    context: Context,
    title: String? = null,
    message: String? = null,
    work: () -> Unit,
) {
    AlertDialog.Builder(context)
        .apply { title?.let { setTitle(it) } }
        .apply { message?.let { setMessage(it) } }
        .setPositiveButton("OK") { dialog, _ ->
            work.invoke()
            dialog.dismiss()
        }
        .show()
}
