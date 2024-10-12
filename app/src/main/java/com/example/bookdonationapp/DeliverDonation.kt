package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DeliverDonation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliver_donation)

        val returnBtn = findViewById<Button>(R.id.Return)

        returnBtn.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}