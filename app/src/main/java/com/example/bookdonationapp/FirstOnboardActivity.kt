package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class FirstOnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_onboard)

        val arrowbutton = findViewById<ImageView>(R.id.arrowBtn)
        val skipbutton = findViewById<TextView>(R.id.startBtn)

        arrowbutton.setOnClickListener {
            startActivity(Intent(this, SecondOnboardActivity::class.java))
        }

        skipbutton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}