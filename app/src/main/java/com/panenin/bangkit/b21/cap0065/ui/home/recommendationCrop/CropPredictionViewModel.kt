package com.panenin.bangkit.b21.cap0065.ui.home.recommendationCrop

import android.graphics.Paint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.charts.Chart
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.BuildConfig
import com.panenin.bangkit.b21.cap0065.data.PriceItems
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class CropPredictionViewModel : ViewModel() {
    private val listPredictions = MutableLiveData<JSONArray>()
    var statusFailure = MutableLiveData<Boolean?>()

    fun setCropPrediction(city: String, commodity: String, duration: String) {

        val url = "${BuildConfig.PANENIN_URL}panen?kota=$city&crop=$commodity&bulan=$duration"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val resultArray = JSONArray(result)
                    listPredictions.postValue(resultArray)
                    statusFailure.value = false
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    statusFailure.value = true
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
                statusFailure.value = true
            }
        })
    }

    fun getCropPredictions(): LiveData<JSONArray> {
        return listPredictions
    }

    fun deleteCropPredictions(){
        val dummyList = JSONArray()
        listPredictions.postValue(dummyList)
    }

}