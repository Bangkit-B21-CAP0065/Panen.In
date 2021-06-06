package com.panenin.bangkit.b21.cap0065.ui.home.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.data.WeatherItems
import com.panenin.bangkit.b21.cap0065.databinding.WeatherPredictItemsBinding

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private val mData = ArrayList<WeatherItems>()

    fun setData(items: ArrayList<WeatherItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): WeatherAdapter.WeatherViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.weather_predict_items, viewGroup, false)
        return WeatherViewHolder(mView)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = WeatherPredictItemsBinding.bind(itemView)
        fun bind(weatherItems: WeatherItems) {
            binding.averageTemperature.text = "${weatherItems.temperature}°C"
            binding.conditionText.text = weatherItems.description
            binding.minTemperature.text = "${weatherItems.temperatureMin}°C"
            binding.maxTemperature.text = "${weatherItems.temperatureMax}°C"
            binding.averageHumidityText.text = "${weatherItems.humidity}% RH"
            binding.windVelocityText.text = "${weatherItems.windSpeed} m/sec"
            binding.windDirectionText.text = "${weatherItems.windDegree}°"
            binding.sunIntensityText.text = weatherItems.sunIntensity
        }
    }

}