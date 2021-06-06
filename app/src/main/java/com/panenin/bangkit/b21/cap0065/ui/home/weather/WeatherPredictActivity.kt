package com.panenin.bangkit.b21.cap0065.ui.home.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityWeatherPredictBinding
import java.util.*

class WeatherPredictActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: ActivityWeatherPredictBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        weatherViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(WeatherViewModel::class.java)

        showLoading(false)
        binding.btnInputCity.setOnClickListener {
            val city = binding.inputText.text.toString()
            if (city.isEmpty()) return@setOnClickListener
            showLoading(true)
            weatherViewModel.setWeather(city)
            binding.textLocation.text = city
            closeKeyBoard()
        }

        weatherViewModel.getWeathers().observe(this, { weatherItems ->
            val searchCity = binding.inputText.text.toString()
            binding.textLocation.text = searchCity
            if (weatherItems != null) {
                adapter.setData(weatherItems)
                showLoading(false)
            } else{
                Toast.makeText(this@WeatherPredictActivity, "Silahkan masukan input terlebih dahulu", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })

    supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_weather_prediction_page)
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbarPrediction.visibility = View.VISIBLE
        } else {
            binding.progressbarPrediction.visibility = View.GONE
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