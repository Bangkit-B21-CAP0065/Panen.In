package com.panenin.bangkit.b21.cap0065.ui.home

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityRecommendationCropBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray


class RecommendationCropActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var chosenRegion : String
    private lateinit var chosenPlantType : String
    private lateinit var chosenDuration : String
    private lateinit var binding: ActivityRecommendationCropBinding
    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barChart = binding.barChart
        barChart.setNoDataText("Hitung prediksi sekarang !")
        val p: Paint = barChart.getPaint(Chart.PAINT_INFO)
        p.textSize = 60f
        barChart.invalidate()

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

        binding.predictCropButton.setOnClickListener{
            getCropPrediction(chosenRegion, chosenPlantType, chosenDuration)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_reccomendation_page)
    }

    private fun getCropPrediction(city: String, commodity: String, duration: String) {
        var tempDurationNumber = duration.toInt()

        val url = "http://34.101.212.102/api/panen?kota=$city&crop=$commodity&bulan=$duration"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    Toast.makeText(this@RecommendationCropActivity,
                            "Rekomendasi bibit $chosenPlantType untuk wilayah $chosenRegion, durasi: $chosenDuration x 4 bulan berhasil dimuat !", Toast.LENGTH_LONG).show()
                    //parsing json
                    val result = String(responseBody)
                    val resultArray = JSONArray(result)
                    setBarChart(tempDurationNumber, resultArray)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                barChart.setNoDataText("Mohon maaf, data tidak dapat diprediksi")
                val p: Paint = barChart.getPaint(Chart.PAINT_INFO)
                p.textSize = 60f
                barChart.invalidate()
                Toast.makeText(this@RecommendationCropActivity, "Mohon maaf, data tidak dapat diprediksi", Toast.LENGTH_SHORT).show()
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    private fun setBarChart(numberOfBar: Int, dataPrediction: JSONArray) {
        var countPredictionNumber = dataPrediction.length()

        val entries = ArrayList<BarEntry>()
        for(i in 0 until countPredictionNumber){
            val dataPrediction = dataPrediction[i]
            var dataFloatPrediction = if (dataPrediction is Double) dataPrediction.toFloat()
                                  else dataPrediction as Float
            entries.add(BarEntry(dataFloatPrediction, i))
        }

        val barDataSet = BarDataSet(entries, "Prediksi dalam satuan ton")

        val labels = ArrayList<String>()
        for (i in 1..countPredictionNumber) {
            labels.add("Round $i")
        }

        val data = BarData(labels, barDataSet)
        barChart.data = data // set the data and list of lables into chart
        barDataSet.color = ResourcesCompat.getColor(getResources(), R.color.yellow_500, null)
        barChart.setDescription("")
        barChart.animateY(5000)
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