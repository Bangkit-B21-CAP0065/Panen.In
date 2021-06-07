package com.panenin.bangkit.b21.cap0065.ui.home.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.data.WeatherItems
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class WeatherViewModel: ViewModel() {
    private val listWeathers = MutableLiveData<ArrayList<WeatherItems>>()
    var statusFailure = MutableLiveData<Boolean?>()

    fun setWeather(city: String) {
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
                    weatherItems.city = city
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
                    listWeathers.postValue(listItems)
                    statusFailure.value = false
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
                statusFailure.value = true
            }
        })
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

    class TimeInHours(val hours: Int, val minutes: Int, val seconds: Int) {
        override fun toString(): String {
            return String.format("%dh %02dm %02ds", hours, minutes, seconds)
        }
    }

    fun getWeathers(): LiveData<ArrayList<WeatherItems>> {
        return listWeathers
    }
}