package com.panenin.bangkit.b21.cap0065.ui.home.marketVisualization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panenin.bangkit.b21.cap0065.data.PriceItems
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class MarketPriceViewModel : ViewModel() {
    private val listPrices = MutableLiveData<ArrayList<PriceItems>>()
    var statusFailure = MutableLiveData<Boolean?>()

    fun setCommodityPrices(year: String, city: String, commodity: String) {
        val listPrice = ArrayList<PriceItems>()

        val url = "http://34.101.212.102/api/harga?kota=$city&crop=$commodity&tahun=$year"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    Log.d("VIEW MODEL", "SUKSESSSSS")
                    //parsing json
                    val result = String(responseBody)
                    val resultArray = JSONArray(result)
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
                    listPrices.postValue(listPrice)
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

    fun getCommodityPrices(): LiveData<ArrayList<PriceItems>> {
        return listPrices
    }

    fun deleteCommodityPrices(){
        val listPrice = ArrayList<PriceItems>()
        listPrices.postValue(listPrice)
    }

}