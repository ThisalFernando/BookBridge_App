package com.example.bookdonationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.First

class DonateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        val arrowBackBtn = findViewById<ImageView>(R.id.arrowBack)
        val userBtn = findViewById<ImageView>(R.id.userIcon)
        val continueBtn = findViewById<Button>(R.id.Continue)

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        arrowBackBtn.setOnClickListener {
            startActivity(Intent(this, SubjectActivity::class.java))
        }

        continueBtn.setOnClickListener {
            startActivity(Intent(this, First::class.java))
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
    }
}
