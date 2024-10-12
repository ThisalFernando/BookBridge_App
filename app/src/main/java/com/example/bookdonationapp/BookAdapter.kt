package com.example.bookdonationapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

//Define an interface for click events
interface OnBookActionListner{
    fun onDonateClick(book: BookData)
    fun onPayClick(book: BookData)
}

class BookAdapter(var bookList: ArrayList<BookData>,
    private val bookActionListner: OnBookActionListner) :
    RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    // To store the original full book list
    private var fullBookList: ArrayList<BookData> = ArrayList(bookList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_list,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bookList[position]
        holder.bookImage.setImageResource(currentItem.bookImage)
        holder.bGrade.text = currentItem.bookGrade
        holder.bName.text = currentItem.bookName
        holder.bCount.text = currentItem.bookCount.toString()

        //Set click listeners for the donate and pay buttons
        holder.donateBtn.setOnClickListener {
            if (currentItem.bookCount > 0) {
                currentItem.bookCount--  // Decrease book count
                holder.bCount.text = currentItem.bookCount.toString()  // Update UI
            }
            bookActionListner.onDonateClick(currentItem)
        }

        holder.payBtn.setOnClickListener {
            if (currentItem.bookCount > 0) {
                currentItem.bookCount--  // Decrease book count
                holder.bCount.text = currentItem.bookCount.toString()  // Update UI
            }
            bookActionListner.onPayClick(currentItem)
        }
    }

    //search function
    fun filter(query: String) {
        val filteredList = ArrayList<BookData>()
        if (query.isEmpty()) {
            filteredList.addAll(fullBookList)
        } else {
            val filterPattern = query.lowercase().trim()
            for (book in fullBookList) {
                if (book.bookGrade.lowercase().contains(filterPattern)) {
                    filteredList.add(book)
                }
            }
        }
        bookList.clear()
        bookList.addAll(filteredList)
        notifyDataSetChanged()
    }

    class MyViewHolder(ItemView : View): RecyclerView.ViewHolder(ItemView){

        val bookImage : ShapeableImageView = itemView.findViewById(R.id.book_image)
        val bGrade : TextView = itemView.findViewById(R.id.bGrade)
        val bName : TextView = itemView.findViewById(R.id.bName)
        val bCount : TextView = itemView.findViewById(R.id.bCount)
        val donateBtn: Button = itemView.findViewById(R.id.donateBtn)
        val payBtn: Button = itemView.findViewById(R.id.payBtn)

    }

}