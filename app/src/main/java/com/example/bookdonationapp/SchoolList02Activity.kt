package com.example.bookdonationapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView

class SchoolList02Activity : AppCompatActivity() {

    private lateinit var sclRecyclerView: RecyclerView
    private lateinit var sclArrayList: ArrayList<SchoolData>
    private lateinit var adapter: SchoolAdapter
    private lateinit var searchView: SearchView
    lateinit var imageid: Array<Int>
    lateinit var sclname: Array<String>
    lateinit var scladd: Array<String>
    lateinit var sclmilage: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_list01)

        val arrowBackBtn = findViewById<ImageView>(R.id.arrowBack)
        val userBtn = findViewById<ImageView>(R.id.userIcon)

        userBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        arrowBackBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }


        // Set up the RecyclerView
        sclRecyclerView = findViewById(R.id.recyclerView)
        sclRecyclerView.layoutManager = LinearLayoutManager(this)
        sclRecyclerView.setHasFixedSize(true)

        sclArrayList = arrayListOf<SchoolData>()
        getSchoolData()

        adapter = SchoolAdapter(sclArrayList)
        sclRecyclerView.adapter = adapter

        // Set up the SearchView
        searchView = findViewById(R.id.search)
        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        // Set the text color
        searchEditText.setTextColor(Color.BLACK)
        // Set the hint text color
        searchEditText.setHintTextColor(Color.GRAY)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do nothing here as we are handling changes in real-time with onQueryTextChange
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterList(newText ?: "")
                return true
            }
        })

        // Sort the list by distance
        sclArrayList.sortBy { it.sclMilage }

        // Handle item clicks
        adapter.setOnItemClickListner(object : SchoolAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                val selectedSchoolName = sclArrayList[position].sclName
                //Send school name using shared Pref
                val sharedPreferences = getSharedPreferences("SchoolPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("selectedSchoolName", selectedSchoolName)
                editor.apply()

                val intent = Intent(this@SchoolList02Activity, LanguageActivity::class.java)
                //Send data to the next page by intent
                intent.putExtra("schoolName", selectedSchoolName)
                startActivity(intent)
            }
        })
    }

    // Display items
    private fun getSchoolData() {
        imageid = arrayOf(
            R.drawable.s5,
            R.drawable.s6,
            R.drawable.s7,
            R.drawable.s8,
            R.drawable.s13,
            R.drawable.s14,
            R.drawable.s15
        )

        sclname = arrayOf(
            "Rahula School",
            "Sujatha College",
            "Shariputhra College",
            "President's Collage",
            "Matara Central College",
            "St. Mary's Convent",
            "Mahamaya Vidyalaya"
        )

        scladd = arrayOf(
            "Akuressa Rd, Matara",
            "Rahula Rd, Matara",
            "Shariputhra Mawatha, Matara",
            "Matara Road, Matara",
            "Rahula Rd, Matara",
            "Akuressa Rd, Matara",
            "Beach Rd, Matara"
        )

        sclmilage = arrayOf(
            "0.7 mi",
            "0.5 mi",
            "2.3 mi",
            "1.2 mi",
            "0.6 mi",
            "0.4 mi",
            "3.2 mi"
        )

        for (i in imageid.indices) {
            val scl = SchoolData(imageid[i], sclname[i], scladd[i], sclmilage[i])
            sclArrayList.add(scl)
        }
    }
}