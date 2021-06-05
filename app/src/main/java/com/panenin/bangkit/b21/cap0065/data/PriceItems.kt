package com.panenin.bangkit.b21.cap0065.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceItems (
    var year: String? = "0000",
    var month: Int? = 0,
    var averagePrice: String? = "0",
    var minPrice: String? = "0",
    var maxPrice: String? = "0"
): Parcelable
