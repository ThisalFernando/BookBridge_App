package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val logoutTxt = findViewById<TextView>(R.id.logoutTxt)
        val bookicon = findViewById<ImageView>(R.id.bookIcon)
        val homeicon = findViewById<ImageView>(R.id.homeIcon)

        logoutBtn.setOnClickListener{
            Firebase.auth.signOut()

            startActivity(Intent(this, WelcomeActivity::class.java))
        }

        logoutTxt.setOnClickListener{
            Firebase.auth.signOut()

            startActivity(Intent(this, WelcomeActivity::class.java))
        }

        bookicon.setOnClickListener{
            startActivity(Intent(this, LanguageActivity::class.java))
        }

        homeicon.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}