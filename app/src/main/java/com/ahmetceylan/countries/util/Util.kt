package com.ahmetceylan.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahmetceylan.countries.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.databinding.BindingAdapter

//Extension

fun ImageView.downloadFromUrl(url : String,progressDrawable: CircularProgressDrawable){

    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)

}

fun placeholderProgressBar(context: Context) : CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius =40f
        start()
    }

}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?) {
    url?.let {
        view.downloadFromUrl(it, placeholderProgressBar(view.context))
    }
}



