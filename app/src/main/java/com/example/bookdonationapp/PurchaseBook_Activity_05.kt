package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.databinding.ActivityPurchaseBook05Binding
import com.journeyapps.barcodescanner.CaptureActivity

class PurchaseBook_Activity_05 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook05Binding

    // Registering Activity Result Launcher for the QR Code scanner
    private val qrScannerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val scannedData = result.data?.getStringExtra("SCAN_RESULT")
        if (result.resultCode == RESULT_OK && scannedData != null) {
            // QR code successfully scanned, navigate to PurchaseBook_Activity_04
            Toast.makeText(this, "Scanned QR Code: $scannedData", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PurchaseBook_Activity_04::class.java).apply {
                putExtra("SCANNED_CODE", scannedData) // Pass the scanned data if needed
            }
            startActivity(intent)
        } else {
            // Scan was cancelled or failed
            Toast.makeText(this, "Failed to scan QR code", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBook05Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle the QR Code CardView click to open the scanner
        binding.cardView4.setOnClickListener {
            openQrScanner()
        }

        // Handle the "Make another Donation" button click
        binding.makeAnotherDonationButton.setOnClickListener {
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }

        // Handle the "View History" button click
        binding.viewHistoryButton.setOnClickListener {
            val intent = Intent(this, PurchaseBook_Activity_06::class.java)
            startActivity(intent)
        }
    }

    private fun openQrScanner() {
        // Launch the QR code scanner
        val intent = Intent(this, CaptureActivity::class.java)
        qrScannerLauncher.launch(intent)
    }
}