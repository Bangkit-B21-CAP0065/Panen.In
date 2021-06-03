package com.panenin.bangkit.b21.cap0065.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityMarketVisualizationBinding
import com.panenin.bangkit.b21.cap0065.databinding.ActivityRecommendationCropBinding


class MarketVisualizationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var chosenYear : String
    private lateinit var chosenRegion : String
    private lateinit var chosenCommodity : String
    private lateinit var chosenPricesPosition : String
    private lateinit var mChart: LineChart
    var lineData: LineData? = null
    var lineDataSet: LineDataSet? = null
    var lineEntries = ArrayList<Entry>()
    private lateinit var binding: ActivityMarketVisualizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketVisualizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_visualization_page)

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

        val adapterPricesChoosed = ArrayAdapter.createFromResource(
                this,
                R.array.prices_list,
                android.R.layout.simple_spinner_item
        )
        adapterPricesChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.yearDropdown.adapter = adapterYearChoosed
        binding.yearDropdown.onItemSelectedListener = this

        binding.regionDropdown.adapter = adapterRegionChoosed
        binding.regionDropdown.onItemSelectedListener = this

        binding.commodityDropdown.adapter = adapterPlantTypeChoosed
        binding.commodityDropdown.onItemSelectedListener = this

        binding.pricesDropdown.adapter = adapterPricesChoosed
        binding.pricesDropdown.onItemSelectedListener = this

        chosenYear =  resources.getStringArray(R.array.year_list).first()
        chosenRegion = resources.getStringArray(R.array.region_visualization_list).first()
        chosenCommodity = resources.getStringArray(R.array.plant_type_list).first()

        binding.visualizationButton.setOnClickListener{
            Toast.makeText(
                this,
                "Tahun: $chosenYear, region: $chosenRegion, tipe: $chosenCommodity",
                Toast.LENGTH_SHORT
            ).show()
            setLineBar()
        }
    }

    private fun setLineBar() {
        mChart = binding.chart
//        with(mChart) {
//            // (1)
//            axisLeft.isEnabled = false
//            axisRight.isEnabled = false
//            xAxis.isEnabled = false
//            legend.isEnabled = false
//
//            // (2)
//            setTouchEnabled(true)
//            isDragEnabled = true
//            setScaleEnabled(false)
//            setPinchZoom(true)
//        }
        lineEntries.add(Entry(2f, 0))
        lineEntries.add(Entry(4f, 1))
        lineEntries.add(Entry(6f, 1))
        lineEntries.add(Entry(8f, 3))
        lineEntries.add(Entry(7f, 4))
        lineEntries.add(Entry(3f, 3))

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        lineDataSet = LineDataSet(lineEntries, "Cells")
        lineData = LineData(labels, lineDataSet)
        mChart.data = lineData // set the data and list of lables into chart

        lineDataSet!!.color = ResourcesCompat.getColor(getResources(), R.color.yellow_500, null);

        mChart.animateY(5000)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.year_dropdown -> {
                Toast.makeText(
                    this,
                    "position $position, Year Selected: " + parent.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                chosenYear = parent.selectedItem.toString()
            }
            R.id.region_dropdown -> {
                Toast.makeText(
                    this,
                    "position $position, Region Selected: " + parent.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                chosenRegion = parent.selectedItem.toString()
            }
            R.id.commodity_dropdown -> {
                Toast.makeText(
                    this,
                    "position $position, Commodity type Selected: " + parent.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                chosenCommodity = parent.selectedItem.toString()
            }
            R.id.prices_dropdown -> {
                Toast.makeText(
                        this,
                        "position $position, Prices type Selected: " + parent.selectedItem.toString(),
                        Toast.LENGTH_SHORT
                ).show()
                chosenPricesPosition = position.toString()
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