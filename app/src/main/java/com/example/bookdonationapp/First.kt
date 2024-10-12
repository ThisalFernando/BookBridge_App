package com.example

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

data class QRCodeData(val schoolName: String, val bookGrade: String, val bookName: String, val orderId: String, val filePath: String)

class First : AppCompatActivity() {
    private lateinit var orderIdInput: EditText
    private lateinit var generateQrButton: Button
    private lateinit var qrCodeImage: ImageView
    private lateinit var downloadAndContinueButton: Button

    private val gson = Gson()
    private val sharedPref by lazy { getSharedPreferences("qr_codes", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        orderIdInput = findViewById(R.id.orderIdInput)
        generateQrButton = findViewById(R.id.generateQrButton)
        qrCodeImage = findViewById(R.id.qrCodeImage)
        downloadAndContinueButton = findViewById(R.id.downloadAndContinueButton)

        generateQrButton.setOnClickListener {
            val orderId = orderIdInput.text.toString()
            if (orderId.isNotEmpty()) {
                generateAndDisplayQRCode(orderId)
                downloadAndContinueButton.visibility = Button.VISIBLE // Show the button
            } else {
                Toast.makeText(this, "Please enter an Order ID", Toast.LENGTH_SHORT).show()
            }
        }

        downloadAndContinueButton.setOnClickListener {
            qrCodeImage.drawable?.let { drawable ->
                val bitmap = (drawable as BitmapDrawable).bitmap
                if (saveImage(bitmap, orderIdInput.text.toString())) {
                    // Navigate to the OK page after successfully saving the image
                    navigateToOkPage()
                } else {
                    Toast.makeText(this, "Failed to save QR Code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun generateAndDisplayQRCode(orderId: String) {
        // Retrieve data from SharedPreferences
        val sharedPreferences01 = getSharedPreferences("SchoolPrefs", MODE_PRIVATE)
        val schoolName = sharedPreferences01.getString("selectedSchoolName", "Default School Name") ?: "Default School Name"

        val sharedPreferences02 = getSharedPreferences("BookPrefs", MODE_PRIVATE)
        val bookGrade = sharedPreferences02.getString("selectedBookGrade", "Default Book Grade") ?: "Default Book Grade"
        val bookName = sharedPreferences02.getString("selectedBookName", "Default Book Name") ?: "Default Book Name"

        // Create a JSON string with all the data
        val qrData = mapOf(
            "schoolName" to schoolName,
            "bookGrade" to bookGrade,
            "bookName" to bookName,
            "orderId" to orderId
        )
        val qrDataJson = gson.toJson(qrData)

        // Generate QR Code Bitmap
        val qrCodeBitmap = generateQRCode(qrDataJson)
        if (qrCodeBitmap != null) {
            qrCodeImage.setImageBitmap(qrCodeBitmap)
        } else {
            Toast.makeText(this, "Failed to generate QR Code.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateQRCode(data: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bmp
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveImage(bitmap: Bitmap, orderId: String): Boolean {
        val filename = "QRCode_$orderId.png"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir, filename)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Save QR code data to SharedPreferences
            saveQRData(
                QRCodeData(
                    schoolName = getSchoolName(),
                    bookGrade = getBookGrade(),
                    bookName = getBookName(),
                    orderId = orderId,
                    filePath = file.absolutePath
                )
            )

            Toast.makeText(this, "QR Code saved to ${file.absolutePath}", Toast.LENGTH_SHORT).show()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun saveQRData(qrData: QRCodeData) {
        val qrListType = object : TypeToken<MutableList<QRCodeData>>() {}.type
        val qrList: MutableList<QRCodeData> =
            gson.fromJson(sharedPref.getString("qr_list", "[]"), qrListType)
        qrList.add(qrData)
        sharedPref.edit().putString("qr_list", gson.toJson(qrList)).apply()
    }

    private fun getSchoolName(): String {
        val sharedPreferences01 = getSharedPreferences("SchoolPrefs", MODE_PRIVATE)
        return sharedPreferences01.getString("selectedSchoolName", "Default School Name") ?: "Default School Name"
    }

    private fun getBookGrade(): String {
        val sharedPreferences02 = getSharedPreferences("BookPrefs", MODE_PRIVATE)
        return sharedPreferences02.getString("selectedBookGrade", "Default Book Grade") ?: "Default Book Grade"
    }

    private fun getBookName(): String {
        val sharedPreferences02 = getSharedPreferences("BookPrefs", MODE_PRIVATE)
        return sharedPreferences02.getString("selectedBookName", "Default Book Name") ?: "Default Book Name"
    }

    private fun navigateToOkPage() {
        val intent = Intent(this, Ok::class.java)
        startActivity(intent)
        finish() // Optional: Call finish if you don't want to come back to this activity
    }
}
