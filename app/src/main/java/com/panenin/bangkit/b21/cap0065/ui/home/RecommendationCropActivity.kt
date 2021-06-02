package com.panenin.bangkit.b21.cap0065.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityRecommendationCropBinding


class RecommendationCropActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var chosenRegion : String
    private lateinit var chosenPlantType : String
    private lateinit var chosenDuration : String
    private lateinit var binding: ActivityRecommendationCropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationCropBinding.inflate(layoutInflater)
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

        chosenRegion = resources.getStringArray(R.array.region_list).first().toLowerCase()
        chosenPlantType = resources.getStringArray(R.array.plant_type_list).first().toLowerCase()
        chosenDuration =  resources.getStringArray(R.array.duration_list).first()

        binding.predictWeatherButton.setOnClickListener{
            var tempDurationNumber = chosenDuration.toInt()
            setBarChart(tempDurationNumber)
            Toast.makeText(this,
                "region: $chosenRegion, tipe: $chosenPlantType, durasi: $chosenDuration", Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_reccomendation_page)
    }

    private fun setBarChart(numberOfBar : Int) {
        val entries = ArrayList<BarEntry>()
//        entries.add(BarEntry(8f, 0))
//        entries.add(BarEntry(2f, 1))
//        entries.add(BarEntry(5f, 2))
//        entries.add(BarEntry(20f, 3))
//        entries.add(BarEntry(15f, 4))
//        entries.add(BarEntry(19f, 5))

        val barDataSet = BarDataSet(entries, "Cells")

        val labels = ArrayList<String>()
        for (i in 1..numberOfBar) {
            entries.add(BarEntry(8f, i-1))
            labels.add("Round $i")
        }
//        labels.add("18-Jan")
//        labels.add("19-Jan")
//        labels.add("20-Jan")
//        labels.add("21-Jan")
//        labels.add("22-Jan")
//        labels.add("23-Jan")
        val data = BarData(labels, barDataSet)
        binding.barChart.data = data // set the data and list of lables into chart

        barDataSet.color = ResourcesCompat.getColor(getResources(), R.color.yellow_500, null);

        binding.barChart.animateY(5000)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.region_dropdown -> {
                Toast.makeText(this, "position $position, Region Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenRegion = parent.selectedItem.toString().toLowerCase()
            }
            R.id.plant_type_dropdown -> {
                Toast.makeText(this, "position $position, Plan type Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenPlantType = parent.selectedItem.toString().toLowerCase()
            }
            R.id.duration_dropdown -> {
                Toast.makeText(this, "position $position, Duration Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenDuration = parent.selectedItem.toString()
            }
        }
    }

    // function to the button on press
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

}