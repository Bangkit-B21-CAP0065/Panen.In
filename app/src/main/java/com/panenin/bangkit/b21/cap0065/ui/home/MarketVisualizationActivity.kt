package com.panenin.bangkit.b21.cap0065.ui.home

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
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.data.PriceItems
import com.panenin.bangkit.b21.cap0065.databinding.ActivityMarketVisualizationBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray


class MarketVisualizationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var chosenYear : String
    private lateinit var chosenRegion : String
    private lateinit var chosenCommodity : String
    private lateinit var chosenPricesPosition : String
    private lateinit var mChart: LineChart
    var lineData: LineData? = null
    var lineDataSet: LineDataSet? = null
    private lateinit var binding: ActivityMarketVisualizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketVisualizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_visualization_page)
        mChart = binding.chart
        mChart.setNoDataText("Cari visualisasi harga pasar sekarang !")
        val p: Paint = mChart.getPaint(Chart.PAINT_INFO)
        p.textSize = 60f
        mChart.invalidate()

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
            getCommodityPrices(chosenYear, chosenRegion.toLowerCase(), chosenCommodity.toLowerCase(), chosenPricesPosition)
        }
    }

    private fun getCommodityPrices(year: String, city: String, commodity: String, priceStatus: String) {
        val listPrice = ArrayList<PriceItems>()

        val url = "http://34.101.212.102/api/harga?kota=$city&crop=$commodity&tahun=$year"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val resultArray = JSONArray(result)
                    Log.d("visualization ACTIVITY", "$resultArray")
                    for (i in 0 until resultArray.length()) {
                        val dataObject = resultArray.getJSONObject(i)
                        val priceItem = PriceItems()
                        priceItem.year = dataObject.getString("tahun")
                        priceItem.month = dataObject.getString("bulan").toInt()
                        priceItem.averagePrice = dataObject.getString("harga_rerata")
                        priceItem.minPrice = dataObject.getString("harga_terendah")
                        priceItem.maxPrice = dataObject.getString("harga_tertinggi")
                        listPrice.add(priceItem)
                    }
                    listPrice.sortBy { price -> price.month }
                    listPrice.forEach { println(it) }
                    setLineBar(priceStatus, listPrice)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                mChart.setNoDataText("Mohon maaf, data belum tersedia.")
                val p: Paint = mChart.getPaint(Chart.PAINT_INFO)
                p.textSize = 60f
                mChart.invalidate()
                Toast.makeText(this@MarketVisualizationActivity, "Mohon maaf, data belum tersedia.", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    private fun setLineBar(priceStatus: String, listPrice: ArrayList<PriceItems>) {
//    private fun setLineBar(priceStatus: String) {
        with(mChart) {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(true)
        }
        when(priceStatus){
            "0" -> {
                Log.d("MARKET", "price status 0 = harga rerata")
            }
            "1" -> {
                Log.d("MARKET", "price status 1 = harga tertinggi")
            }
            "2" -> {
                Log.d("MARKET", "price status 2 = harga terendah")
            }
        }
        var lineEntries = ArrayList<Entry>()
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

        lineDataSet = LineDataSet(lineEntries, "Harga dalam Rupiah")
        lineData = LineData(labels, lineDataSet)
        mChart.data = lineData // set the data and list of lables into chart
        mChart.setDescription("")
        lineDataSet?.color = ResourcesCompat.getColor(getResources(), R.color.yellow_500, null);
        mChart.animateY(5000)
        mChart.invalidate()
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