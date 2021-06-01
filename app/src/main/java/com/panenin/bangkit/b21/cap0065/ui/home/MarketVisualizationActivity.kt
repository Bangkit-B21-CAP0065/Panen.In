package com.panenin.bangkit.b21.cap0065.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityMarketVisualizationBinding

class MarketVisualizationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMarketVisualizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterYearChoosed = ArrayAdapter.createFromResource(
            this,
            R.array.year_list,
            android.R.layout.simple_spinner_item
        )
        adapterYearChoosed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.yearDropdown.adapter = adapterYearChoosed
        binding.yearDropdown.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.year_dropdown -> {
                Toast.makeText(this, "position $position, Year Selected: " + parent.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}