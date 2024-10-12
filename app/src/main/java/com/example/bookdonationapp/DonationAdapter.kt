package com.example.bookdonationapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookdonationapp.databinding.ItemDonationBinding

class DonationAdapter(private val donationList: List<DonationRecord>) :
    RecyclerView.Adapter<DonationAdapter.DonationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val binding = ItemDonationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DonationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        val donation = donationList[position]
        holder.bind(donation)
    }

    override fun getItemCount(): Int = donationList.size

    class DonationViewHolder(private val binding: ItemDonationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donation: DonationRecord) {
            binding.bookNameText.text = donation.bookName
            binding.schoolNameText.text = donation.schoolName
            binding.dateText.text = donation.date
        }
    }

}