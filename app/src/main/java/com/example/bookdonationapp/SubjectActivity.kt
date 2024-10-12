package com.example.bookdonationapp

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SubjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        val arrowBackBtn = findViewById<ImageView>(R.id.arrowBack)
        val subOpt01Btn = findViewById<Button>(R.id.subOpt01)
        val subOpt02Btn = findViewById<Button>(R.id.subOpt02)
        val subOpt03Btn = findViewById<Button>(R.id.subOpt03)
        val subOpt04Btn = findViewById<Button>(R.id.subOpt04)
        val subOpt05Btn = findViewById<Button>(R.id.subOpt05)
        val homeBtn = findViewById<ImageView>(R.id.homeIcon)
        val userBtn = findViewById<ImageView>(R.id.userIcon)

        arrowBackBtn.setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java))
        }

        homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        subOpt01Btn.setOnClickListener {
            startActivity(Intent(this, BookStore01Activity::class.java))
        }

        subOpt02Btn.setOnClickListener {
            startActivity(Intent(this, BookStore02Activity::class.java))
        }

        subOpt03Btn.setOnClickListener {
            startActivity(Intent(this, BookStore03Activity::class.java))
        }

        subOpt04Btn.setOnClickListener {
            startActivity(Intent(this, BookStore04Activity::class.java))
        }

        subOpt05Btn.setOnClickListener {
            startActivity(Intent(this, BookStore05Activity::class.java))
        }
    }
}