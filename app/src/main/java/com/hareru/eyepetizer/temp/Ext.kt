package com.hareru.eyepetizer.temp

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup

fun ViewGroup.deepLink(actionUrl: String) {
    setOnClickListener {
        val uri = Uri.parse(actionUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}
