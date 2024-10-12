package com.example.bookdonationapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookStoreAdapter(
    private val bookStores: List<BookStore>,
    private val onItemClick: (BookStore) -> Unit
) : RecyclerView.Adapter<BookStoreAdapter.BookStoreViewHolder>() {

    class BookStoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.bookstoreLogo)
        val price: TextView = itemView.findViewById(R.id.bookstorePrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookStoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_store, parent, false)
        return BookStoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookStoreViewHolder, position: Int) {
        val bookStore = bookStores[position]
        holder.logo.setImageResource(bookStore.logoResource)
        holder.price.text = bookStore.price

        holder.itemView.setOnClickListener {
            onItemClick(bookStore)
        }
    }

    override fun getItemCount(): Int = bookStores.size
}


data class BookStore(
    val name: String,
    val logoResource: Int,
    val price: String,
    val url: String
)