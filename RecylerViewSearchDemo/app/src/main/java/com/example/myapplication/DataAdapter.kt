package com.example.myapplication

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList

class DataAdapter( private val listener: (DataModel) -> Unit) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>(), Filterable {

    private var mArrayList: ArrayList<DataModel>
    private val mFilteredList: ArrayList<DataModel>
    private var mSearchFilter: SearchFilter? = null
    private var mSearchTerm: String? = null

    init {
        mArrayList = ArrayList()
        mFilteredList = ArrayList()
    }

    fun add(mArrayList: ArrayList<DataModel>) {
        this.mArrayList.addAll(mArrayList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val androidVersion = mArrayList[i]
        viewHolder.bind(androidVersion, listener)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    // Filterable method
    override fun getFilter(): Filter {
        if (mSearchFilter == null) {
            mFilteredList.clear()
            mFilteredList.addAll(mArrayList)
            mSearchFilter = SearchFilter(mFilteredList)
        }
        return mSearchFilter!!
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById<View>(R.id.tv_name) as TextView

        private val root: MaterialCardView = view.findViewById<View>(R.id.root) as MaterialCardView

        private val delete: ImageView = view.findViewById<View>(R.id.imageView) as ImageView

        fun bind(androidVersion: DataModel, listener: (DataModel) -> Unit) {
            tvName.text = androidVersion.name
            delete.setOnClickListener {
                val pos = adapterPosition

                // Remove item from Adapter List and notify
                mArrayList.removeAt(pos)
                notifyItemRemoved(pos)

                // Remove Object from Original Data from SearchFilter Class
                if (mSearchFilter != null) {
                    mSearchFilter!!.updateList(androidVersion)
                }
            }

            root.setOnClickListener {

                listener(androidVersion)
            }

        }

    }

    internal inner class SearchFilter(private val listToFilter: MutableList<DataModel>) : Filter() {
        // Update Base Search List on Item Removed while searching
        fun updateList(dataModel: DataModel) {
            listToFilter.remove(dataModel)
        }

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val searchString = constraint.toString().toLowerCase().trim { it <= ' ' }
            mSearchTerm = searchString
            val results = FilterResults()
            if (TextUtils.isEmpty(searchString)) {
                results.values = listToFilter
            } else {
                val filteredList: MutableList<DataModel> = ArrayList()
                for (dm in listToFilter) {
                    if (dm.name.toLowerCase().contains(searchString)) {
                        filteredList.add(dm)
                    }
                }
                results.values = filteredList
            }
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            mArrayList = results.values as ArrayList<DataModel>
            notifyDataSetChanged()
        }
    }

}