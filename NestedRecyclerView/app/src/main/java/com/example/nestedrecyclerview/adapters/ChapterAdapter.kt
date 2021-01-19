package com.example.nestedrecyclerview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerview.R
import com.example.nestedrecyclerview.adapters.ChapterAdapter.CustomViewHolder
import com.example.nestedrecyclerview.models.Chapter
import com.squareup.picasso.Picasso
import java.util.*

class ChapterAdapter(context: Context, private val chapters: ArrayList<Chapter>) :
    RecyclerView.Adapter<CustomViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View = inflater.inflate(R.layout.single_chapter, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val chapter = chapters[position]
        holder.bind(chapter)

    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chapter: Chapter) {
            tvChapterName.text = chapter.chapterName
             Picasso.get().load(chapter.imageUrl).into(ivChapter)
        }

        var ivChapter: ImageView = itemView.findViewById<View>(R.id.ivChapter) as ImageView
        var tvChapterName: TextView = itemView.findViewById<View>(R.id.tvChapterName) as TextView

    }

}