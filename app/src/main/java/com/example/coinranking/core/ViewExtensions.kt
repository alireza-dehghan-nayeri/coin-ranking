package com.example.coinranking.core

import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun DrawerLayout.hideDrawer() {
    setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
}

fun DrawerLayout.showDrawer() {
    setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
}

fun ImageView.loadUrl(url: String) {

    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .dispatcher(Dispatchers.IO)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
