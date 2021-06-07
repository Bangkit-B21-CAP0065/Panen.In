package com.panenin.bangkit.b21.cap0065.ui.home.recommendationCrop

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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityRecommendationCropBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray


class CropRecommendationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var chosenRegion : String
    private lateinit var chosenPlantType : String
    private lateinit var chosenDuration : String
    private lateinit var binding: ActivityRecommendationCropBinding
    private lateinit var barChart: BarChart
    private lateinit var cropPredictionViewModel: CropPredictionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_reccomendation_page)

        barChart = binding.barChart
        barChart.setNoDataText("Hitung prediksi sekarang !")
        val p: Paint = barChart.getPaint(Chart.PAINT_INFO)
        p.textSize = 60f
        barChart.invalidate()

        cropPredictionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CropPredictionViewModel::class.java)

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
            cropPredictionViewModel.setCropPrediction(chosenRegion, chosenPlantType, chosenDuration)
        }

        cropPredictionViewModel.getCropPredictions().observe(this, { resultArrayPrediction ->
            if(resultArrayPrediction != null){
                setBarChart(resultArrayPrediction)
            }
        })

        cropPredictionViewModel.statusFailure.observe(this, { statusFailure ->
            statusFailure?.let{
                if(statusFailure == true){
                    cropPredictionViewModel.deleteCropPredictions()
                    barChart.setNoDataText("Mohon maaf, data tidak dapat diprediksi")
                    val paint: Paint = barChart.getPaint(Chart.PAINT_INFO)
                    paint.textSize = 60f
                    barChart.invalidate()
                    Toast.makeText(this@CropRecommendationActivity, "Mohon maaf, data tidak dapat diprediksi", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setBarChart(dataPrediction: JSONArray) {
        Toast.makeText(this@CropRecommendationActivity,
            "Rekomendasi bibit $chosenPlantType untuk wilayah $chosenRegion, durasi: $chosenDuration x 4 bulan berhasil dimuat !", Toast.LENGTH_LONG).show()
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
            R.id.region_dropdown -> chosenRegion = parent.selectedItem.toString().toLowerCase()
            R.id.plant_type_dropdown -> chosenPlantType = parent.selectedItem.toString().toLowerCase()
            R.id.duration_dropdown -> chosenDuration = parent.selectedItem.toString()
        }
    }

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