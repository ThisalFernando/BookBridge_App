package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val loginbutton = findViewById<Button>(R.id.login)

        loginbutton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}