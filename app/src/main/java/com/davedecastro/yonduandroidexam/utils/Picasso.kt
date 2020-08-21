package com.davedecastro.yonduandroidexam.utils

import android.widget.ImageView
import com.davedecastro.yonduandroidexam.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun String?.into(imageView: ImageView) {
    CoroutineScope(Dispatchers.Main).launch {
        if (this@into.isNullOrEmpty()) {
            Picasso.get()
                .load(R.drawable.broken_image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.broken_image)
                .into(imageView)
        } else {
            Picasso.get()
                .load(this@into)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.broken_image)
                .into(imageView)
        }
    }
}