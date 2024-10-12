package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.HomeActivity
import com.example.bookdonationapp.ProfileActivity
import com.example.bookdonationapp.R
import com.example.bookdonationapp.SelectCenter

class Ok : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ok)

        // Find the button by ID
        val continueButton = findViewById<Button>(R.id.find)
        val SelectCenterButton = findViewById<Button>(R.id.button_select_center)
        val userbtn = findViewById<ImageView>(R.id.userIcon)
        val homeBtn = findViewById<ImageView>(R.id.homeIcon)

        // Set an OnClickListener on the button
        continueButton.setOnClickListener {
            // Start the QR Details activity
            val intent = Intent(this, QR_ListActivity::class.java)
            startActivity(intent)
        }

        SelectCenterButton.setOnClickListener {
            // Start the QR Details activity
            val intent_sc = Intent(this, SelectCenter::class.java)
            startActivity(intent_sc)
        }

        userbtn.setOnClickListener {
            // Start the QR Details activity
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        homeBtn.setOnClickListener {
            // Start the QR Details activity
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
