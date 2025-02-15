package com.example.coinranking.core

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun bindImageUrl(imageView: ImageView, url: String) {
    imageView.loadUrl(url)
}

