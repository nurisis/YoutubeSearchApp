package com.hinuri.youtubesearchapp.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import com.hinuri.youtubesearchapp.R

@BindingAdapter("imagePath")
fun ImageView.loadImage(@Nullable path:String?) {
    Glide.with(this)
        .load(path ?: "")
        .placeholder(R.color.whiteGrey)
        .error(R.color.whiteGrey)
        .centerCrop()
        .into(this)
}

@BindingConversion
fun convertBooleanToVisibility(visible:Boolean) :Int {
    return if(visible) View.VISIBLE else View.GONE
}