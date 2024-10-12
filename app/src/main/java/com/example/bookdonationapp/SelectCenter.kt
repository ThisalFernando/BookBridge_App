package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookdonationapp.R

class SelectCenter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_center)
        setupButtons()
    }

    private fun setupButtons() {
        findViewById<ImageView>(R.id.title_image_colombo).setOnClickListener {
            openMapActivity("Colombo", 6.9271, 79.9612) // Colombo coordinates
        }

        findViewById<ImageView>(R.id.title_image_kandy).setOnClickListener {
            openMapActivity("Kandy", 7.2906, 80.6337) // Kandy coordinates
        }

        findViewById<ImageView>(R.id.title_image_galle).setOnClickListener {
            openMapActivity("Galle", 6.0634, 80.2190) // Galle coordinates
        }

        findViewById<ImageView>(R.id.title_image_jaffna).setOnClickListener {
            openMapActivity("Jaffna", 9.6618, 80.0080) // Jaffna coordinates
        }

        findViewById<ImageView>(R.id.title_image_nuwara_eliya).setOnClickListener {
            openMapActivity("Nuwara Eliya", 6.9500, 80.7880) // Nuwara Eliya coordinates
        }

        findViewById<ImageView>(R.id.title_image_kalutara).setOnClickListener {
            openMapActivity("Kalutara", 6.5854, 79.9607) // Kilinochchi coordinates
        }

        findViewById<ImageView>(R.id.title_image_kegalle).setOnClickListener {
            openMapActivity("Kegalle", 7.2513, 80.3464) // Polonnaruwa coordinates
        }

        findViewById<ImageView>(R.id.title_image_trincomalee).setOnClickListener {
            openMapActivity("Trincomalee", 8.5709, 81.2091) // Trincomalee coordinates
        }

        findViewById<ImageView>(R.id.title_image_puttalam).setOnClickListener {
            openMapActivity("Puttalam", 8.2494, 79.8340) // Puttalam coordinates
        }

        findViewById<ImageView>(R.id.title_image_kurunegala).setOnClickListener {
            openMapActivity("Kurunegala", 7.4850, 80.3595) // Kurunegala coordinates
        }

        findViewById<ImageView>(R.id.title_image_ratnapura).setOnClickListener {
            openMapActivity("Ratnapura", 6.7056, 80.3847) // Vavuniya coordinates
        }

        findViewById<ImageView>(R.id.title_image_matara).setOnClickListener {
            openMapActivity("Matara", 5.9497, 80.5469) // Matara coordinates
        }

        findViewById<ImageView>(R.id.title_image_badulla).setOnClickListener {
            openMapActivity("Badulla", 6.9665, 81.0564) // Badulla coordinates
        }

        findViewById<ImageView>(R.id.title_image_ampara).setOnClickListener {
            openMapActivity("Gampaha", 7.1065, 79.9765) // Gampaha coordinates
        }

        findViewById<ImageView>(R.id.title_image_ampara).setOnClickListener {
            openMapActivity("Ampara", 7.3109, 81.6725) // Ampara coordinates
        }

        findViewById<ImageView>(R.id.title_image_mannar).setOnClickListener {
            openMapActivity("Mannar", 9.0754, 79.9887) // Mannar coordinates
        }

        findViewById<ImageView>(R.id.homeIcon).setOnClickListener {
            val intent_home = Intent(this, HomeActivity::class.java)
            startActivity(intent_home)
        }

    }

    private fun openMapActivity(cityName: String, latitude: Double, longitude: Double) {
        val intent = Intent(this, MapActivity::class.java).apply {
            putExtra("CITY_NAME", cityName)
            putExtra("LATITUDE", latitude)
            putExtra("LONGITUDE", longitude)
        }
        startActivity(intent)
    }
}