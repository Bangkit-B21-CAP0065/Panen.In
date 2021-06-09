package com.panenin.bangkit.b21.cap0065.ui.shopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panenin.bangkit.b21.cap0065.R
import com.panenin.bangkit.b21.cap0065.databinding.ActivityPaymentConfirmationBinding

class PaymentConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConfirm.setOnClickListener{
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
        }
    }
}