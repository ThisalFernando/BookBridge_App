package com.example.bookdonationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookdonationapp.databinding.ActivityPurchaseBook01Binding

class PurchaseBook_Activity_01 : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseBook01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBook01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderNumberInput = binding.orderNumberInput
        val confirmNumberInput = binding.confirmNumberInput
        val enterButton = binding.enterButton
        val confirmButton = binding.confirmButton

        enterButton.setOnClickListener {
            val orderNumber = orderNumberInput.text.toString()
            if (orderNumber.isEmpty()) {
                Toast.makeText(this, "Please enter an order number", Toast.LENGTH_SHORT).show()
            } else {
                // Process the order number
                Toast.makeText(this, "Order number entered", Toast.LENGTH_SHORT).show()
            }
        }

        confirmButton.setOnClickListener {
            val orderNumber = orderNumberInput.text.toString()
            val confirmNumber = confirmNumberInput.text.toString()

            if (orderNumber == confirmNumber && orderNumber.isNotEmpty()) {
                navigateToPurchaseActivity02()
            } else {
                Toast.makeText(this, "Order numbers do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToPurchaseActivity02() {
        val orderNumber = binding.orderNumberInput.text.toString()
        val intent = Intent(this, PurchaseBook_Activity_02::class.java).apply {
            putExtra("ORDER_NUMBER", orderNumber)
        }
        startActivity(intent)
    }
}