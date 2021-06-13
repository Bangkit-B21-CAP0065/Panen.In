package com.panenin.bangkit.b21.cap0065.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.panenin.bangkit.b21.cap0065.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener{
            val intent = Intent(this, WelcomeActivity2::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
    }
}