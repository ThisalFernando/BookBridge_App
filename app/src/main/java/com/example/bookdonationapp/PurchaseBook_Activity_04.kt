package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.databinding.ActivityPurchaseBook04Binding
import kotlin.random.Random

class PurchaseBook_Activity_04 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook04Binding

    // List of possible delivery statuses
    private val deliveryStatuses = listOf(
        "Order Placed", "Order processing", "Shipped", "In Transit",
        "Out for Delivery", "Delivered", "Pending", "On Hold",
        "Failed Delivery Attempted"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBook04Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Generate a random delivery status from the list
        val deliveryStatus = getRandomDeliveryStatus()
        binding.deliveryStatusText.text = deliveryStatus

        // Set up the "Okey" button click listener
        binding.okeyButton.setOnClickListener {
            val intent = Intent(this, PurchaseBook_Activity_05::class.java)
            startActivity(intent)
        }

        // Set up the "Make another Donation" button click listener
        binding.makeAnotherDonationButton.setOnClickListener {
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to get a random delivery status from the list
    private fun getRandomDeliveryStatus(): String {
        return deliveryStatuses[Random.nextInt(deliveryStatuses.size)]
    }
}