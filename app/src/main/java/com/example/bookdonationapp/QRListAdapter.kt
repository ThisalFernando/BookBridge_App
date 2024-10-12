package com.example.bookdonationapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.QRCodeData
import com.example.bookdonationapp.R

class QRListAdapter(private val qrList: List<QRCodeData>) : RecyclerView.Adapter<QRListAdapter.QRViewHolder>() {

    class QRViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val qrImageView: ImageView = itemView.findViewById(R.id.qrImageView)
        val qrTextView: TextView = itemView.findViewById(R.id.qrTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qr_card, parent, false)
        return QRViewHolder(view)
    }

    override fun onBindViewHolder(holder: QRViewHolder, position: Int) {
        val qrData = qrList[position]
        holder.qrTextView.text = "Order ID: ${qrData.orderId}"

        // Load the QR code image from the file path
        val bitmap = BitmapFactory.decodeFile(qrData.filePath)
        holder.qrImageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int = qrList.size
}
