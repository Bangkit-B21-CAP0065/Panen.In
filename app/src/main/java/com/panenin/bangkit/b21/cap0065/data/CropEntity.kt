package com.panenin.bangkit.b21.cap0065.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CropEntity(
        var id: String,
        var title: String,
        var price: String,
        var imgPoster: Int,
)   : Parcelable