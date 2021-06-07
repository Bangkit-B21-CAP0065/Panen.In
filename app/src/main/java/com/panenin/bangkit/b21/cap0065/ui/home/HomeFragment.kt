package com.panenin.bangkit.b21.cap0065.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.panenin.bangkit.b21.cap0065.databinding.FragmentHomeBinding
import com.panenin.bangkit.b21.cap0065.ui.home.marketVisualization.MarketVisualizationActivity
import com.panenin.bangkit.b21.cap0065.ui.home.recommendationCrop.RecommendationCropActivity
import com.panenin.bangkit.b21.cap0065.ui.home.weather.WeatherPredictActivity

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.buttonPredictWeather.setOnClickListener{
            Toast.makeText(requireContext(), "button predict clicked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, WeatherPredictActivity::class.java)
            startActivity(intent)
        }
        binding.buttonRecommendCrop.setOnClickListener{
            Toast.makeText(requireContext(), "button recommend clicked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, RecommendationCropActivity::class.java)
            startActivity(intent)
        }
        binding.buttonMarketVisualization.setOnClickListener{
            Toast.makeText(requireContext(), "button market visualization works!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MarketVisualizationActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}