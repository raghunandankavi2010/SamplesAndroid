package com.example.myapplication

import android.text.TextUtils
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.testscroll.R
import com.google.android.material.card.MaterialCardView


class DataAdapter(
        private val listener: (DataModel) -> Unit,
        private val checkListener: (DataModel) -> Unit,
        private val unCheckListener: (DataModel) -> Unit
) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>(), Filterable {

    private var mArrayList: ArrayList<DataModel>
    private val mFilteredList: ArrayList<DataModel>
    private var temp: ArrayList<DataModel> = ArrayList()
    private var mSearchFilter: SearchFilter? = null
    private var mSearchTerm: String? = null
    private var selected: ArrayList<DataModel> = ArrayList()

    init {
        mArrayList = ArrayList()
        mFilteredList = ArrayList()
    }

    fun add(mArrayList: ArrayList<DataModel>) {
        this.mArrayList.addAll(mArrayList)
        notifyDataSetChanged()
    }

    fun selectAllItems() {
        for (i in 0 until mArrayList.size) {
           // mArrayList[i].isChecked = true
            selected.add(mArrayList[i])
        }
        notifyDataSetChanged()
    }

    fun unSelectAllItems() {
        for (i in 0 until mArrayList.size) {
            //mArrayList[i].isChecked = false
            selected.remove(mArrayList[i])
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val androidVersion = mArrayList[i]
        viewHolder.bind(androidVersion, listener, checkListener, unCheckListener)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    // Filterable method
    override fun getFilter(): Filter {
        if (mSearchFilter == null) {
            mFilteredList.clear()
            mFilteredList.addAll(mArrayList)
            temp = mArrayList
            mSearchFilter = SearchFilter(mFilteredList)
        }
        return mSearchFilter!!
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById<View>(R.id.tv_name) as TextView

        private val root: MaterialCardView = view.findViewById<View>(R.id.root) as MaterialCardView



        fun bind(
                androidVersion: DataModel,
                listener: (DataModel) -> Unit,
                checkListener: (DataModel) -> Unit,
                unCheckListener: (DataModel) -> Unit
        ) {
            tvName.text = androidVersion.name

            root.setOnClickListener {

                listener(androidVersion)
            }
        }
    }

    inner class SearchFilter(private val listToFilter: MutableList<DataModel>) : Filter() {
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