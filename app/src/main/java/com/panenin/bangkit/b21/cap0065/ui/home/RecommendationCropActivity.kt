package com.panenin.bangkit.b21.cap0065.ui.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityRecommendationCropBinding


class RecommendationCropActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var chosenRegion : String
    private lateinit var chosenPlantType : String
    private lateinit var chosenDuration : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecommendationCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterRegionChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.region_list,
            android.R.layout.simple_spinner_item
        )
        adapterRegionChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterPlantTypeChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.plant_type_list,
            android.R.layout.simple_spinner_item
        )
        adapterPlantTypeChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterDurationChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.duration_list,
            android.R.layout.simple_spinner_item
        )
        adapterDurationChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.regionDropdown.adapter = adapterRegionChoosed
        binding.regionDropdown.onItemSelectedListener = this

        binding.plantTypeDropdown.adapter = adapterPlantTypeChoosed
        binding.plantTypeDropdown.onItemSelectedListener = this

        binding.durationDropdown.adapter = adapterDurationChoosed
        binding.durationDropdown.onItemSelectedListener = this

        chosenRegion = resources.getStringArray(R.array.region_list).first()
        chosenPlantType = resources.getStringArray(R.array.plant_type_list).first()
        chosenDuration =  resources.getStringArray(R.array.duration_list).first()

        binding.predictWeatherButton.setOnClickListener{
            Toast.makeText(this,
                "region: $chosenRegion, tipe: $chosenPlantType, durasi: $chosenDuration", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.region_dropdown -> {
                Toast.makeText(this, "position $position, Region Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenRegion = parent.selectedItem.toString()
            }
            R.id.plant_type_dropdown -> {
                Toast.makeText(this, "position $position, Plan type Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenPlantType = parent.selectedItem.toString()
            }
            R.id.duration_dropdown -> {
                Toast.makeText(this, "position $position, Duration Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenDuration = parent.selectedItem.toString()
            }
        }
    }

}