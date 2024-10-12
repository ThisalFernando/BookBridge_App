package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookdonationapp.databinding.ActivityPurchaseBook06Binding

class PurchaseBook_Activity_06 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook06Binding
    private lateinit var donationAdapter: DonationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBook06Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView with adapter
        donationAdapter = DonationAdapter(getDonationRecords())
        binding.donationRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.donationRecyclerView.adapter = donationAdapter

        // Handle the "Make another Donation" button click
        binding.makeDonationButton.setOnClickListener {
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDonationRecords(): List<DonationRecord> {
        // Fetch donation records from the database or any data source
        // This is a placeholder for demonstration
        return listOf(
            DonationRecord("Book 1", "School A", "2024-10-05", "qr_code_1.png"),
            DonationRecord("Book 2", "School B", "2024-10-06", "qr_code_2.png"),
            DonationRecord("Book 3", "School C", "2024-10-07", "qr_code_3.png")
        )
    }
}