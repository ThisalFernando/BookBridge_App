package com.example.bookdonationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PayActivity : AppCompatActivity() {

    private lateinit var payBookStoreRecyclerView: RecyclerView

    // List of bookstores with their details
    private val bookStores = listOf(
        BookStore("Samudra Book Shop", R.drawable.samudra_dp, "Rs. 400.00", "https://www.samudra.lk"),
        BookStore("Sarasavi Book Shop", R.drawable.sarasavi_dp, "Rs. 420.00", "https://www.sarasavi.lk"),
        BookStore("MyBookstore.lk", R.drawable.mybookstore_dp, "Rs. 400.00", "https://www.mybookstore.lk"),
        BookStore("CeyBooks", R.drawable.ceybooks_dp, "Rs. 400.00", "https://www.ceybooks.lk"),
        BookStore("SLBooks.lk", R.drawable.slbooks_dp, "Rs. 400.00", "https://www.slbooks.lk"),
        BookStore("Expographic", R.drawable.expographic_dp, "Rs. 400.00", "https://www.expographic.lk"),
        BookStore("ACIS Bookstore", R.drawable.acis_dp, "Rs. 400.00", "https://www.acis.lk"),
        BookStore("Book Studio", R.drawable.bookstudio_dp, "Rs. 400.00", "https://www.bookstudio.lk")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val arrowBackBtn = findViewById<ImageView>(R.id.arrowBack)
        val userBtn = findViewById<ImageView>(R.id.userIcon)

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        arrowBackBtn.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

        /*val bookGrade = intent.getStringExtra("bookGrade")
        val bookName = intent.getStringExtra("bookName")
        val bookCount = intent.getStringExtra("bookCount")*/
        val sharedPreferences01 = getSharedPreferences("SchoolPrefs", Context.MODE_PRIVATE)
        val  schoolNameFromPrefs = sharedPreferences01.getString("selectedSchoolName", "Default School Name")
        val sharedPreferences02 = getSharedPreferences("BookPrefs", Context.MODE_PRIVATE)
        val bookImageFromPrefs = sharedPreferences02.getInt("selectedBookImage", R.drawable.book_icon)
        val bookGradeFromPrefs = sharedPreferences02.getString("selectedBookGrade", "Default Book Grade")
        val bookNameFromPrefs = sharedPreferences02.getString("selectedBookName", "Default Book Name")

        // Display the data
        val imageView = findViewById<ImageView>(R.id.imageBook)
        imageView.setImageResource(bookImageFromPrefs)
        findViewById<TextView>(R.id.bookGradeText).text = bookGradeFromPrefs
        findViewById<TextView>(R.id.bookNameText).text = bookNameFromPrefs
        findViewById<TextView>(R.id.SchoolNameText).text = schoolNameFromPrefs

        // Setup the RecyclerView for displaying bookstores
        payBookStoreRecyclerView = findViewById(R.id.payBookStoreRecyclerView)
        payBookStoreRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val adapter = BookStoreAdapter(bookStores) { selectedBookStore ->
            val intent = Intent(this, PurchaseBook_Activity_00::class.java).apply {
                putExtra("bookStoreName", selectedBookStore.name)
                putExtra("bookStorePrice", selectedBookStore.price)
                putExtra("bookStoreImage", selectedBookStore.logoResource)
                putExtra("bookStoreUrl", selectedBookStore.url)
            }
            startActivity(intent)
        }

        payBookStoreRecyclerView.adapter = adapter
    }
}