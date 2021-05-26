package com.hareru.eyepetizer.ui.webview

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.hareru.eyepetizer.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data!!
        val title = data.getQueryParameter("title")
        val url = data.getQueryParameter("url")
        val customTabsIntent = CustomTabsIntent.Builder()
                .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
