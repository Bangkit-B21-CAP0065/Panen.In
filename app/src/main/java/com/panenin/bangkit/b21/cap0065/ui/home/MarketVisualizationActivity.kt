package com.panenin.bangkit.b21.cap0065.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityMarketVisualizationBinding

class MarketVisualizationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var chosenYear : String
    private lateinit var chosenRegion : String
    private lateinit var chosenCommodity : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMarketVisualizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterYearChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.year_list,
            android.R.layout.simple_spinner_item
        )
        adapterYearChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterRegionChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.region_visualization_list,
            android.R.layout.simple_spinner_item
        )
        adapterRegionChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterPlantTypeChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.plant_type_list,
            android.R.layout.simple_spinner_item
        )
        adapterPlantTypeChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.yearDropdown.adapter = adapterYearChoosed
        binding.yearDropdown.onItemSelectedListener = this

        binding.regionDropdown.adapter = adapterRegionChoosed
        binding.regionDropdown.onItemSelectedListener = this

        binding.commodityDropdown.adapter = adapterPlantTypeChoosed
        binding.commodityDropdown.onItemSelectedListener = this

        chosenYear =  resources.getStringArray(R.array.year_list).first()
        chosenRegion = resources.getStringArray(R.array.region_visualization_list).first()
        chosenCommodity = resources.getStringArray(R.array.plant_type_list).first()

        binding.visualizationButton.setOnClickListener{
            Toast.makeText(this,
                "Tahun: $chosenYear, region: $chosenRegion, tipe: $chosenCommodity", Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_visualization_page)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.year_dropdown -> {
                Toast.makeText(this, "position $position, Year Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenYear = parent.selectedItem.toString()
            }
            R.id.region_dropdown -> {
                Toast.makeText(this, "position $position, Region Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenRegion = parent.selectedItem.toString()
            }
            R.id.commodity_dropdown -> {
                Toast.makeText(this, "position $position, Commodity type Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                chosenCommodity = parent.selectedItem.toString()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
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