package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ThirdOnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_onboard)

        val arrowbutton = findViewById<ImageView>(R.id.arrowBtn)
        val startbutton = findViewById<TextView>(R.id.startBtn)

        arrowbutton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        startbutton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}