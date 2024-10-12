package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.databinding.ActivityPurchaseBook02Binding

class PurchaseBook_Activity_02 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use View Binding to inflate the layout
        binding = ActivityPurchaseBook02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the order number from the intent
        val orderNumber = intent.getStringExtra("ORDER_NUMBER") ?: "DefaultOrderNumber"

        // Set up the continue button click listener
        binding.continueButton.setOnClickListener {
            // Navigate to the next activity (PurchaseBook_Activity_03) with the order number
            navigateToPurchaseBookActivity03(orderNumber)
        }
    }

    private fun navigateToPurchaseBookActivity03(orderNumber: String) {
        val intent = Intent(this, PurchaseBook_Activity_03::class.java).apply {
            putExtra("ORDER_NUMBER", orderNumber)
        }
        startActivity(intent)
    }
}