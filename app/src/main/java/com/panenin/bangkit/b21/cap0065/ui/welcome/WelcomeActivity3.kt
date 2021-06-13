package com.panenin.bangkit.b21.cap0065.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panenin.bangkit.b21.cap0065.MainActivity
import com.panenin.bangkit.b21.cap0065.databinding.ActivityWelcome3Binding

class WelcomeActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWelcome3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
    }
}