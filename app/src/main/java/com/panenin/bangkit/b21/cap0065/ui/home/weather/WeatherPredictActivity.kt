package com.panenin.bangkit.b21.cap0065.ui.home.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.data.WeatherItems
import com.panenin.bangkit.b21.cap0065.databinding.ActivityWeatherPredictBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class WeatherPredictActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: ActivityWeatherPredictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.progressbarPrediction.visibility = View.GONE
        binding.btnInputCity.setOnClickListener {
            val city = binding.inputText.text.toString()
            if (city.isEmpty()) return@setOnClickListener
            binding.progressbarPrediction.visibility = View.VISIBLE
            setWeather(city)
            binding.textLocation.text = city
            closeKeyBoard()
        }

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

    private fun setWeather(city: String) {
        val listItems = ArrayList<WeatherItems>()

        val apiKey = "874f6acc3c0bad4f9c019e59f8d8c6bc"
        val url = "https://api.openweathermap.org/data/2.5/weather?appid=$apiKey&q=$city,id&lang=id&units=metric"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val listWeather = responseObject.getJSONArray("weather")
                    val weather = listWeather.getJSONObject(0)
                    val weatherItems = WeatherItems()
                    weatherItems.id = weather.getInt("id")
                    weatherItems.description = weather.getString("description")

                    val main = responseObject.getJSONObject("main")
                    weatherItems.temperature = main.getDouble("temp").toString()
                    weatherItems.temperatureMin = main.getDouble("temp_min").toString()
                    weatherItems.temperatureMax = main.getDouble("temp_max").toString()
                    weatherItems.humidity = main.getInt("humidity").toString()

                    val wind = responseObject.getJSONObject("wind")
                    weatherItems.windSpeed = wind.getDouble("speed").toString()
                    weatherItems.windDegree = wind.getDouble("deg").toString()

                    val sys = responseObject.getJSONObject("sys")
                    val sunrise = sys.getLong("sunrise")
                    val sunset = sys.getLong("sunset")

                    val resultIntensity = convertFromDuration(sunset - sunrise)
                    weatherItems.sunIntensity = resultIntensity.toString()

                    listItems.add(weatherItems)

                    //set data ke adapter
                    adapter.setData(listItems)
                    binding.progressbarPrediction.visibility = View.GONE
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
                binding.progressbarPrediction.visibility = View.GONE
                Toast.makeText(this@WeatherPredictActivity, "Mohon maaf, kota tidak ditemukan.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    class TimeInHours(val hours: Int, val minutes: Int, val seconds: Int) {
        override fun toString(): String {
            return String.format("%dh %02dm %02ds", hours, minutes, seconds)
        }
    }

    fun convertFromDuration(timeInSeconds: Long): TimeInHours {
        var time = timeInSeconds
        val hours = time / 3600
        time %= 3600
        val minutes = time / 60
        time %= 60
        val seconds = time
        return TimeInHours(hours.toInt(), minutes.toInt(), seconds.toInt())
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