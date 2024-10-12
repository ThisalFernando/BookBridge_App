@file:Suppress("DEPRECATION")

package com.example

import com.example.bookdonationapp.QRDetailsActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.ImageView
import android.widget.Toast
import com.example.bookdonationapp.R
import com.example.bookdonationapp.SubjectActivity
import com.example.bookdonationapp.WelcomeToCollectionCenter

class QRScannerActivity : AppCompatActivity() {

    private lateinit var cameraIcon: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner) // Ensure this matches your XML file name

        // Initialize the ImageView
        cameraIcon = findViewById(R.id.cameraIcon)

        // Set click listener on the camera icon
        cameraIcon.setOnClickListener {
            initiateQRScan()
        }


    }

    private fun initiateQRScan() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0) // Use a specific camera of the device
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // Navigate to the QR Details Activity with the scanned QR code details
                val intent = Intent(this, QRDetailsActivity::class.java)
                intent.putExtra("qrData", result.contents)
                startActivity(intent)
                // Optionally finish this activity if you don't want to return to it
                finish()
            } else {
                // Handle scan cancellation or failure
                Toast.makeText(this, "Scan cancelled or failed", Toast.LENGTH_SHORT).show()
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}