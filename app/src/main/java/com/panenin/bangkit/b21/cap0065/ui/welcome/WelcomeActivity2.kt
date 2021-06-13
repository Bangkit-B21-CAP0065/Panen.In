package com.panenin.bangkit.b21.cap0065.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panenin.bangkit.b21.cap0065.databinding.ActivityWelcome2Binding

class WelcomeActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWelcome2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener{
            val intent = Intent(this, WelcomeActivity3::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
    }
}