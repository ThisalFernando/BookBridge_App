package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val loginbutton = findViewById<Button>(R.id.loginUser)
        val signupbutton = findViewById<Button>(R.id.signup)
        val loginAgentBtn = findViewById<Button>(R.id.loginCCO)

        loginbutton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        signupbutton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        loginAgentBtn.setOnClickListener {
            startActivity(Intent(this, DonationAgentLogin::class.java))
        }


    }

}