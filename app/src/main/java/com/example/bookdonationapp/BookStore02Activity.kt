package com.example.bookdonationapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookStore02Activity : AppCompatActivity(), OnBookActionListner {

    private lateinit var bRecyclerView: RecyclerView
    private lateinit var bArrayList: ArrayList<BookData>
    private lateinit var bookAdapter: BookAdapter
    lateinit var imageid: Array<Int>
    lateinit var bgrade: Array<String>
    lateinit var bname: Array<String>
    lateinit var bcount: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_store02)

        val arrowBackBtn = findViewById<ImageView>(R.id.arrowBack)
        val userBtn = findViewById<ImageView>(R.id.userIcon)

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        arrowBackBtn.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

        imageid = arrayOf(
            R.drawable.mb01,
            R.drawable.mb02,
            R.drawable.mb03,
            R.drawable.mb04,
            R.drawable.mb05
        )

        bgrade = arrayOf(
            "Grade 06",
            "Grade 07",
            "Grade 08",
            "Grade 09",
            "Grade 10"
        )

        bname = arrayOf(
            "Mathematics Text Book",
            "Mathematics Text Book",
            "Mathematics Text Book",
            "Mathematics Text Book",
            "Mathematics Text Book"
        )

        bcount = arrayOf(
            10,
            15,
            8,
            4,
            3
        )

        bRecyclerView = findViewById(R.id.recyclerView)
        bRecyclerView.layoutManager = LinearLayoutManager(this)
        bRecyclerView.setHasFixedSize(true)

        bArrayList = arrayListOf<BookData>()
        getBookdata()

        //Search View Implementation
        val searchView = findViewById<SearchView>(R.id.search)

        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        // Set the text color
        searchEditText.setTextColor(Color.BLACK)
        // Set the hint text color
        searchEditText.setHintTextColor(Color.GRAY)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bookAdapter.filter(newText ?: "")
                return true
            }

        })
    }

    private fun getBookdata(){

        for(i in imageid.indices){
            val book = BookData(imageid[i], bgrade[i], bname[i], bcount[i])
            bArrayList.add(book)
        }

        bookAdapter = BookAdapter(bArrayList, this)
        bRecyclerView.adapter = bookAdapter
    }

    override fun onDonateClick(book: BookData){
        val intent = Intent(this, DonateActivity::class.java)
        //Send data to the donate page by shared Pref
        val sharedPreferences = getSharedPreferences("BookPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selectedBookImage", book.bookImage)
        editor.putString("selectedBookGrade", book.bookGrade)
        editor.putString("selectedBookName", book.bookName)
        editor.putString("selectedBookCount", book.bookCount.toString())
        editor.apply()
        //Send data to the donate page by intent
        intent.putExtra("bookGrade", book.bookGrade)
        intent.putExtra("bookName", book.bookName)
        intent.putExtra("bookCount", book.bookCount.toString())
        startActivity(intent)
    }

    // Handle pay button click
    override fun onPayClick(book: BookData) {
        val intent = Intent(this, PayActivity::class.java)
        //Send data to the donate page by shared Pref
        val sharedPreferences = getSharedPreferences("BookPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selectedBookImage", book.bookImage)
        editor.putString("selectedBookGrade", book.bookGrade)
        editor.putString("selectedBookName", book.bookName)
        editor.putString("selectedBookCount", book.bookCount.toString())
        editor.apply()
        //Send data to the donate page by intent
        intent.putExtra("bookGrade", book.bookGrade)
        intent.putExtra("bookName", book.bookName)
        intent.putExtra("bookCount", book.bookCount.toString())
        startActivity(intent)
    }

}