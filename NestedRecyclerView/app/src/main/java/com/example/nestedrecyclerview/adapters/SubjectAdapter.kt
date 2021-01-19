package com.example.nestedrecyclerview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerview.R
import com.example.nestedrecyclerview.models.Chapter
import com.example.nestedrecyclerview.models.Subject
import java.util.*

/**
 * Created by ashu on 6/2/17.
 */
class SubjectAdapter(var subjects: ArrayList<Subject>, private val context: Context) :
    RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.single_subject, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subjects[position].chapters,subjects[position])

    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerView: RecyclerView = itemView.findViewById<View>(R.id.rvChapters) as RecyclerView
        var tvHeading: TextView = itemView.findViewById<View>(R.id.tvSubjectName) as TextView

        fun bind(chapters: ArrayList<Chapter>, subject: Subject) {
           recyclerView.adapter = ChapterAdapter(context,chapters)
          recyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
           recyclerView.setHasFixedSize(true)
           tvHeading.text = subject.subjectName
        }

    }

}