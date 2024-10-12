package com.example.bookdonationapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.QRScannerActivity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class QRDetailsActivity : AppCompatActivity() {

    private lateinit var schoolNameTextView: TextView
    private lateinit var bookGradeTextView: TextView
    private lateinit var bookNameTextView: TextView
    private lateinit var orderIdTextView: TextView
    private lateinit var qrCodeImageView: ImageView
    private lateinit var downloadButton: Button
    private lateinit var scanAnotherButton: Button

    private var qrData: QRCodeData? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrdetails)

        // Initialize Views
        schoolNameTextView = findViewById(R.id.schoolNameTextView)
        bookGradeTextView = findViewById(R.id.bookGradeTextView)
        bookNameTextView = findViewById(R.id.bookNameTextView)
        orderIdTextView = findViewById(R.id.orderIdTextView)
        qrCodeImageView = findViewById(R.id.qrCodeImageView)
        downloadButton = findViewById(R.id.downloadButton)
        scanAnotherButton = findViewById(R.id.scanAnotherButton)

        val EmailBtn = findViewById<ImageView>(R.id.payIcon)

        EmailBtn.setOnClickListener {
            startActivity(Intent(this, DeliverDonation::class.java))
        }

        findViewById<ImageView>(R.id.homeIcon).setOnClickListener {
            val intent_home = Intent(this, HomeActivity::class.java)
            startActivity(intent_home)
        }

        // Retrieve and parse QR data
        val qrDataString = intent.getStringExtra("qrData")
        if (qrDataString != null) {
            try {
                qrData = Gson().fromJson(qrDataString, QRCodeData::class.java)
                displayQRDetails()

                // Generate QR Code Bitmap from the data
                val qrBitmap = generateQRCodeBitmap(qrData!!)
                qrCodeImageView.setImageBitmap(qrBitmap)

            } catch (e: JsonSyntaxException) {
                Toast.makeText(this, "Invalid QR data format", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "No QR data found", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Handle Download Button Click
        downloadButton.setOnClickListener {
            qrData?.let {
                val bitmap = (qrCodeImageView.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
                if (bitmap != null) {
                    val filename = "QR_Code_${it.orderId}.png"
                    val success = FileUtils.saveBitmapToDownloads(this, bitmap, filename)
                    if (success) {
                        Toast.makeText(this, "QR Code downloaded successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to download QR Code", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "QR Code image not available", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Handle Scan Another Button Click
        scanAnotherButton.setOnClickListener {
            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun displayQRDetails() {
        qrData?.let {
            schoolNameTextView.text = it.schoolName
            bookGradeTextView.text = it.bookGrade
            bookNameTextView.text = it.bookName
            orderIdTextView.text = it.orderId
        }
    }

    // Helper function to generate QR Code bitmap
    private fun generateQRCodeBitmap(qrData: QRCodeData): Bitmap {
        val qrContent = Gson().toJson(qrData)
        val size = 500 // pixels
        val qrCodeWriter = com.google.zxing.qrcode.QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(qrContent, com.google.zxing.BarcodeFormat.QR_CODE, size, size)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }
}