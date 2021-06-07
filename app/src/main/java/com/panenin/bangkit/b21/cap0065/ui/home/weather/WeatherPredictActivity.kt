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
import com.panenin.bangkit.b21.cap0065.data.WeatherItems
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
            if (weatherItems != null) {
                binding.textLocation.text = weatherItems[0].city
                adapter.setData(weatherItems)
                showLoading(false)
            }
        })

        weatherViewModel.statusFailure.observe(this, { statusFailure ->
            statusFailure?.let{
                val city = binding.inputText.text.toString()
                if(statusFailure == true){
                    binding.textLocation.text = getString(R.string.input_city)
                    Toast.makeText(this@WeatherPredictActivity, getString(R.string.sorry_cannot_find_city), Toast.LENGTH_SHORT).show()
                    showLoading(false)
                    adapter.deleteData()
                } else {
                    binding.textLocation.text = city
                }
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