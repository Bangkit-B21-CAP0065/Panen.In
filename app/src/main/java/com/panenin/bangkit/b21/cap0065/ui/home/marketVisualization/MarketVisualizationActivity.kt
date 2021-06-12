package com.panenin.bangkit.b21.cap0065.ui.home.marketVisualization

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.data.PriceItems
import com.panenin.bangkit.b21.cap0065.databinding.ActivityMarketVisualizationBinding
import java.util.*
import kotlin.collections.ArrayList

class MarketVisualizationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var chosenYear : String
    private lateinit var chosenRegion : String
    private lateinit var chosenCommodity : String
    private lateinit var chosenPricesPosition : String
    private lateinit var chosenPrices : String
    private lateinit var mChart: LineChart
    var lineData: LineData? = null
    var lineDataSet: LineDataSet? = null
    private lateinit var binding: ActivityMarketVisualizationBinding
    private lateinit var marketPriceViewModel: MarketPriceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketVisualizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_visualization_page)
        mChart = binding.chart
        mChart.setNoDataText(getString(R.string.search_prices_visualization_now))
        val p: Paint = mChart.getPaint(Chart.PAINT_INFO)
        p.textSize = 60f
        mChart.invalidate()

        marketPriceViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MarketPriceViewModel::class.java)

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
        chosenPrices = resources.getStringArray(R.array.prices_list).first()
        when(chosenPrices){
            "Rata-rata" -> chosenPricesPosition = "0"
            "Tertinggi" -> chosenPricesPosition = "1"
            "Terendah" -> chosenPricesPosition = "2"
        }

        binding.visualizationButton.setOnClickListener{
            mChart.setNoDataText(getString(R.string.search_prices_visualization_now))
            val p: Paint = mChart.getPaint(Chart.PAINT_INFO)
            p.textSize = 60f
            mChart.invalidate()
            marketPriceViewModel.setCommodityPrices(chosenYear, chosenRegion.toLowerCase(), chosenCommodity.toLowerCase())
        }

        marketPriceViewModel.getCommodityPrices().observe(this, { listPrices ->
            if(listPrices != null){
                setLineBar(listPrices)
            }
        })

        marketPriceViewModel.statusFailure.observe(this, { statusFailure ->
            statusFailure?.let{
                if(statusFailure == true){
                    marketPriceViewModel.deleteCommodityPrices()
                    mChart.setNoDataText(getString(R.string.sorry_data_not_provide))
                    val paint: Paint = mChart.getPaint(Chart.PAINT_INFO)
                    paint.textSize = 60f
                    mChart.invalidate()
                    Toast.makeText(this@MarketVisualizationActivity, getString(R.string.sorry_data_not_provide), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setLineBar(listPrice: ArrayList<PriceItems>) {
        Toast.makeText(this@MarketVisualizationActivity,
            "visualisasi harga $chosenPrices untuk komoditas $chosenCommodity tahun $chosenYear pada wilayah $chosenRegion berhasil ditampilkan !", Toast.LENGTH_LONG).show()
        val countLineNumber = listPrice.size

        with(mChart) {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(true)
            setDescription("")
        }

        val labels = ArrayList<String>()
        for (i in 1..countLineNumber) {
            labels.add("bulan ${listPrice[i-1].month}")
        }

        var lineEntries = ArrayList<Entry>()
        for(i in 0 until countLineNumber){
            var dataFloatPrice = 0f
            when(chosenPricesPosition){
                "0" -> dataFloatPrice = listPrice[i].averagePrice?.toFloat()!!
                "1" -> dataFloatPrice = listPrice[i].maxPrice?.toFloat()!!
                "2" -> dataFloatPrice = listPrice[i].minPrice?.toFloat()!!
            }
            lineEntries.add(Entry(dataFloatPrice, i))
        }

        lineDataSet = LineDataSet(lineEntries, getString(R.string.price_in_rupiah))
        lineData = LineData(labels, lineDataSet)
        mChart.data = lineData // set the data and list of lables into chart
        lineDataSet?.color = ResourcesCompat.getColor(resources, R.color.yellow_500, null);
        mChart.animateY(5000)
        mChart.invalidate()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.year_dropdown -> chosenYear = parent.selectedItem.toString()
            R.id.region_dropdown -> chosenRegion = parent.selectedItem.toString()
            R.id.commodity_dropdown -> chosenCommodity = parent.selectedItem.toString()
            R.id.prices_dropdown -> {
                chosenPricesPosition = position.toString()
                chosenPrices = parent.selectedItem.toString()
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