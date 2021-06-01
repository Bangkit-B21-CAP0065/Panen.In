package com.panenin.bangkit.b21.cap0065.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityRecommendationCropBinding

class RecommendationCropActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecommendationCropBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.city_list, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.spinner.adapter = adapter

    }

    fun getValues(view: View) {
        val binding = ActivityRecommendationCropBinding.inflate(layoutInflater)

        Toast.makeText(this, "Spinner 1 ${binding.spinner.selectedItem} " +
                "\nSpinner 2 ${binding.spinner2.selectedItem}", Toast.LENGTH_LONG).show()
    }

}