package com.example.nestedrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerview.adapters.SubjectAdapter
import com.example.nestedrecyclerview.models.Chapter
import com.example.nestedrecyclerview.models.Subject
import com.example.nestedrecyclerview.utils.enforceSingleScrollDirection
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit  var rvSubject: RecyclerView
    private var subjectAdapter: SubjectAdapter? = null
    private lateinit var subjects: ArrayList<Subject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()

        subjects = prepareData()
        subjectAdapter = SubjectAdapter(subjects, this@HomeActivity)
        val manager = LinearLayoutManager(this@HomeActivity)

        rvSubject.apply{
            layoutManager = manager
            adapter = subjectAdapter
            enforceSingleScrollDirection()
        }

    }

    private fun initComponents() {
        rvSubject = findViewById(R.id.rvSubject)
    }

    private fun prepareData(): ArrayList<Subject> {
        val subjects = ArrayList<Subject>()

        // physics
        val physicsList = ArrayList<Chapter>()
        val chapter1 = Chapter(1, "Atomic power", "https://i.imgur.com/DvpvklR.png")
        val chapter2 =
            Chapter(2, "Theory of relativity", "https://i.imgur.com/DvpvklR.png")
        val chapter3 = Chapter(3, "Magnetic field", "https://i.imgur.com/DvpvklR.png")
        val chapter4 = Chapter(4, "Practical 1", "https://i.imgur.com/DvpvklR.png")
        val chapter5 =
            Chapter(5, "Practical 2", "https://i.imgur.com/DvpvklR.png")
        physicsList.add(chapter1)
        physicsList.add(chapter2)
        physicsList.add(chapter3)
        physicsList.add(chapter4)
        physicsList.add(chapter5)
        val physics = Subject(1, "Physics", physicsList)


        // Chemistry
        val list = ArrayList<Chapter>()
        val chapter6 = Chapter(6, "Chemical bonds", "https://i.imgur.com/DvpvklR.png")
        val chapter7 = Chapter(7, "Sodium", "https://i.imgur.com/DvpvklR.png")
        val chapter8 =
            Chapter(8, "Periodic table", "https://i.imgur.com/DvpvklR.png")
        val chapter9 = Chapter(9, "Acid and Base", "https://i.imgur.com/DvpvklR.png")
        list.add(chapter6)
        list.add(chapter7)
        list.add(chapter8)
        list.add(chapter9)
        val chem = Subject(2, "Chemistry", list)

        // bio
        val bioList = ArrayList<Chapter>()
        val chapter10 = Chapter(10,"Bacteria","https://i.imgur.com/DvpvklR.png")
        val chapter11 = Chapter(11,"DNA and RNA","https://i.imgur.com/DvpvklR.png")
        val chapter12 = Chapter(12,"Study of microscope","https://i.imgur.com/DvpvklR.png")
        val chapter13 = Chapter(13,"Protein and fibers","https://i.imgur.com/DvpvklR.png")
        bioList.add(chapter10)
        bioList.add(chapter11)
        bioList.add(chapter12)
        bioList.add(chapter13)
        val bio = Subject(3,"Biology",bioList)

        // maths
        val mathsList = ArrayList<Chapter>()
        val chapter14 = Chapter(14,"Circle","https://i.imgur.com/DvpvklR.png")
        val chapter15 = Chapter(15,"Geometry","https://i.imgur.com/DvpvklR.png")
        val chapter16 = Chapter(16, "Linear equations", "https://i.imgur.com/DvpvklR.png")
        val chapter17 = Chapter(17, "Graph", "https://i.imgur.com/DvpvklR.png")
        val chapter18 = Chapter(18, "Trigonometry", "https://i.imgur.com/DvpvklR.png")
        mathsList.add(chapter14)
        mathsList.add(chapter18)
        mathsList.add(chapter15)
        mathsList.add(chapter16)
        mathsList.add(chapter17)
        val maths = Subject(4,"Maths",mathsList)

        subjects.add(physics)
        subjects.add(chem)
        subjects.add(maths)
        subjects.add(bio)
        return subjects
    }
}