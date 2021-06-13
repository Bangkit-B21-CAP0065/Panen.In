package com.panenin.bangkit.b21.cap0065.ui.shopping

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.panenin.bangkit.b21.cap0065.data.CropEntity
import com.panenin.bangkit.b21.cap0065.databinding.FragmentShoppingBinding
import com.panenin.bangkit.b21.cap0065.ui.home.weather.WeatherPredictActivity

class ShoppingFragment : Fragment() {

    private lateinit var shoppingBinding: FragmentShoppingBinding
    private lateinit var viewModelCrop: CropViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shoppingBinding = FragmentShoppingBinding.inflate(inflater, container, false)
        return shoppingBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        activity?.let {
            viewModelCrop = ViewModelProvider(it, ViewModelProvider.NewInstanceFactory())[CropViewModel::class.java]
        }

        val listMovie = viewModelCrop.getListCrop()
        setRecycler(listMovie)

        shoppingBinding.checkoutButton.setOnClickListener{
            val intent = Intent(activity, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRecycler(data: List<CropEntity>) {

        shoppingBinding.rvCrops.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CropAdapter()
        }.also {
            it.adapter.let { adapter ->
                when (adapter) {
                    is CropAdapter -> {
                        adapter.setData(data)
                    }
                }
            }
        }
    }
}