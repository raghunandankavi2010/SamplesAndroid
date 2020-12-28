package com.example.myapplication

import android.text.TextUtils
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
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
    private var onBind = false
    var itemStateArray = SparseBooleanArray()

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
            mArrayList[i].isChecked = true
        }
        notifyDataSetChanged()
    }

    fun unSelectAllItems() {
        for (i in 0 until mArrayList.size) {
            mArrayList[i].isChecked = false
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

        private val delete: ImageView = view.findViewById<View>(R.id.imageView) as ImageView

        private val checkBox: CheckBox = view.findViewById<View>(R.id.checkBox) as CheckBox

        fun bind(
            androidVersion: DataModel,
            listener: (DataModel) -> Unit,
            checkListener: (DataModel) -> Unit,
            unCheckListener: (DataModel) -> Unit
        ) {
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

            checkBox.isChecked = itemStateArray.get(adapterPosition, false)

            checkBox.setOnClickListener {
                if (mSearchFilter != null) {
                   var pos = temp.indexOf(androidVersion)
                    if (!itemStateArray[pos, false]) {
                        checkBox.isChecked = true
                        itemStateArray.put(pos, true)
                    } else {
                        checkBox.isChecked = false
                        itemStateArray.put(pos, false)
                    }

                }else {
                    val adapterPosition = adapterPosition
                    if (!itemStateArray[adapterPosition, false]) {
                        checkBox.isChecked = true
                        itemStateArray.put(adapterPosition, true)
                    } else {
                        checkBox.isChecked = false
                        itemStateArray.put(adapterPosition, false)
                    }
                }
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