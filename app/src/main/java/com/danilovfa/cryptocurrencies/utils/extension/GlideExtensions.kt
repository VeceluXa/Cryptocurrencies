package com.danilovfa.cryptocurrencies.utils.extension

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun View.loadImageByUriNonCached(
    image: Uri,
    container: ImageView,
    @DrawableRes placeholder: Int
) {
    Glide.with(this)
        .load(image)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .centerCrop()
        .placeholder(placeholder)
        .into(container)
}

fun View.loadImageByUrlCached(
    imageUrl: String,
    container: ImageView,
    @DrawableRes placeholder: Int
) {
    Glide
        .with(this)
        .load(imageUrl)
        .placeholder(placeholder)
        .circleCrop()
        .into(container)
}