package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class LanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val arrowBackBtn = findViewById<ImageView>(R.id.arrowBack)
        val langOpt01Btn = findViewById<Button>(R.id.langOpt01)
        val langOpt02Btn = findViewById<Button>(R.id.langOpt02)
        val langOpt03Btn = findViewById<Button>(R.id.langOpt03)
        val homeBtn = findViewById<ImageView>(R.id.homeIcon)
        val userBtn = findViewById<ImageView>(R.id.userIcon)

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        arrowBackBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        langOpt01Btn.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

        langOpt02Btn.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

        langOpt03Btn.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

    }
}