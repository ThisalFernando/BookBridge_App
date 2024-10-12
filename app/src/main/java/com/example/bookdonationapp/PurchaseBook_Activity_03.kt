package com.example.bookdonationapp

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.bookdonationapp.databinding.ActivityPurchaseBook03Binding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.CaptureActivity

class PurchaseBook_Activity_03 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook03Binding

    // Registering Activity Result Launcher for the QR Code scanner
    private val qrScannerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val scannedData = result.data?.getStringExtra("SCAN_RESULT")
        if (result.resultCode == RESULT_OK && scannedData != null) {
            Toast.makeText(this, "Scanned QR Code: $scannedData", Toast.LENGTH_SHORT).show()
            // Navigate to PurchaseBook_Activity_04 after successful scan
            val intent = Intent(this, PurchaseBook_Activity_04::class.java)
            intent.putExtra("SCANNED_CODE", scannedData)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Failed to scan QR code", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBook03Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the order number from intent or shared preferences
        val orderNumber = intent.getStringExtra("ORDER_NUMBER") ?: "DefaultOrderNumber"

        // Generate QR code
        generateQrCode(orderNumber)?.let {
            binding.genaratedQrImage.setImageBitmap(it)
        }

        // Set up the download QR code functionality
        binding.downloadIcon.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                saveQRCode(binding.genaratedQrImage.drawable.toBitmap())
            }
        }

        // Set up the scanner button to open the QR code scanner
        binding.openScannerButton.setOnClickListener {
            val intent = Intent(this, CaptureActivity::class.java)
            qrScannerLauncher.launch(intent)
        }
    }

    private fun generateQrCode(text: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveQRCode(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "QR_Code_${System.currentTimeMillis()}.png")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/QR Codes")
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                Toast.makeText(this, "QR code saved to gallery", Toast.LENGTH_SHORT).show()
            }
        } ?: Toast.makeText(this, "Failed to save QR code", Toast.LENGTH_SHORT).show()
    }
}