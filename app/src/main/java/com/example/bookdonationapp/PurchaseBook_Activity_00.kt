package com.example.bookdonationapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.databinding.ActivityPurchaseBook00Binding

class PurchaseBook_Activity_00 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook00Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPurchaseBook00Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the data passed from the previous activity
        val bookStoreName = intent.getStringExtra("bookStoreName") ?: "Default Store"
        val bookStorePrice = intent.getStringExtra("bookStorePrice") ?: "0.00"
        val bookStoreImage = intent.getIntExtra("bookStoreImage", R.drawable.book_icon_on)
        val bookStoreUrl = intent.getStringExtra("bookStoreUrl") ?: "https://www.youtube.com"

        // Set the data to the UI elements
        binding.textViewTitle.text = bookStoreName
        binding.textViewPrice.text = bookStorePrice
        binding.bookImage.setImageResource(bookStoreImage)

        // Set up the visit site button
        binding.visitSiteButton.setOnClickListener {
            // Open the URL in a browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(bookStoreUrl))
            startActivity(browserIntent)

            // Add a slight delay before navigating to PurchaseBook_Activity_01
            binding.visitSiteButton.postDelayed({
                val intent = Intent(this, PurchaseBook_Activity_01::class.java)
                startActivity(intent)
            }, 2000) // Delay of 500 milliseconds
        }

    }
}