package com.panenin.bangkit.b21.cap0065.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ObjectCropHelper {

    const val CROP_TYPE = "TYPE_CROP"

    fun setGlideImage(context: Context, imagePath: Int, imageView: ImageView) {
        Glide.with(context).clear(imageView)
        Glide.with(context).load(imagePath).into(imageView)
    }
}