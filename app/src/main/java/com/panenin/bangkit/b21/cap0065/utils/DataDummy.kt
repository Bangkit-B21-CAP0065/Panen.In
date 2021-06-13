package com.panenin.bangkit.b21.cap0065.utils

import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.data.CropEntity

object DataDummy {

    fun generateDummyCrops(): ArrayList<CropEntity> {
        val crops = ArrayList<CropEntity>()

        crops.add(
            CropEntity(
                "1",
                "Bibit Kopi",
                "Rp 50.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "2",
                "Bibit Jagung",
                "Rp 40.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "3",
                "Bibit Jagung 2",
                "Rp 60.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "4",
                "Bibit Padi",
                "Rp 70.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "5",
                "Bibit Kopi 2",
                "Rp 80.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "6",
                "Bibit Bunga",
                "Rp 70.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "7",
                "Bibit Padi Kampung",
                "Rp 60.000,00",
                R.drawable.bibit_kopi
            )
        )

        crops.add(
            CropEntity(
                "8",
                "Bibit Kopi Kulo",
                "Rp 100.000,00",
                R.drawable.bibit_kopi
            )
        )
        return crops
    }

}