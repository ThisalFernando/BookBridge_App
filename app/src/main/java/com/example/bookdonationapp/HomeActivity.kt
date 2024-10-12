package com.example.bookdonationapp

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val cityOptBtn01 = findViewById<Button>(R.id.cityOpt01)
        val cityOptBtn02 = findViewById<Button>(R.id.cityOpt02)
        val cityOptBtn03 = findViewById<Button>(R.id.cityOpt03)
        val userBtn = findViewById<ImageView>(R.id.userIcon)

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        cityOptBtn01.setOnClickListener {
            startActivity(Intent(this, SchoolList01Activity::class.java))
        }

        cityOptBtn02.setOnClickListener {
            startActivity(Intent(this, SchoolList02Activity::class.java))
        }

        cityOptBtn03.setOnClickListener {
            startActivity(Intent(this, SchoolList03Activity::class.java))
        }
    }
}