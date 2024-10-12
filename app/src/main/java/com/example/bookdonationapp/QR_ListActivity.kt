package com.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.QRCodeData
import com.example.bookdonationapp.QRListAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.bookdonationapp.R

class QR_ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var qrListAdapter: QRListAdapter
    private var qrDataList = mutableListOf<QRCodeData>()

    private val gson = Gson()


    private val sharedPref by lazy { getSharedPreferences("qr_codes", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_list) // Ensure this matches your XML file name

        recyclerView = findViewById(R.id.qrRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve QR data from SharedPreferences
        loadQRData()

        qrListAdapter = QRListAdapter(qrDataList)
        recyclerView.adapter = qrListAdapter
    }

    private fun loadQRData() {
        val qrListType = object : TypeToken<MutableList<QRCodeData>>() {}.type
        qrDataList = gson.fromJson(sharedPref.getString("qr_list", "[]"), qrListType)
    }


}
