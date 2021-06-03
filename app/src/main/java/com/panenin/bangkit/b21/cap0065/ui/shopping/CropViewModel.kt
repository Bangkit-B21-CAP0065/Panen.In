package com.panenin.bangkit.b21.cap0065.ui.shopping

import androidx.lifecycle.ViewModel
import com.panenin.bangkit.b21.cap0065.data.CropEntity
import com.panenin.bangkit.b21.cap0065.utils.DataDummy

class CropViewModel: ViewModel() {

    fun getListCrop(): List<CropEntity> = DataDummy.generateDummyCrops()
}