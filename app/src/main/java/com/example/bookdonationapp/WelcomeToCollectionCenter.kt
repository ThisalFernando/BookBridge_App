package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.QRScannerActivity

class WelcomeToCollectionCenter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_to_collection_center)

        val arrowbutton = findViewById<ImageView>(R.id.arrowBtn)
        val startbutton = findViewById<TextView>(R.id.startBtn)

        arrowbutton.setOnClickListener {
            startActivity(Intent(this, QRScannerActivity::class.java))
        }

        startbutton.setOnClickListener {
            startActivity(Intent(this, QRScannerActivity::class.java))
        }
    }
}