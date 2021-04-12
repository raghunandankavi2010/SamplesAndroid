package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardRowBinding
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class DataAdapter(
        val context: Context,
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

        val binding = CardRowBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
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

    inner class ViewHolder(private val cardRowBinding: CardRowBinding) :
        RecyclerView.ViewHolder(cardRowBinding.root) {

        fun bind(
            androidVersion: DataModel,
            listener: (DataModel) -> Unit,
            checkListener: (DataModel) -> Unit,
            unCheckListener: (DataModel) -> Unit
        ) {
            cardRowBinding.tvName.text = androidVersion.name
            PatternEditableBuilder().addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                    PatternEditableBuilder.SpannableClickedListener { text ->
                        Toast.makeText(context.applicationContext, "Clicked username: $text",
                                Toast.LENGTH_SHORT).show()
                    }).addPattern(Pattern.compile("\\#(\\w+)"), Color.CYAN,
                    PatternEditableBuilder.SpannableClickedListener { text ->
                        Toast.makeText(context.applicationContext,"Clicked hashtag: $text",
                                Toast.LENGTH_SHORT).show()
                    }).into(cardRowBinding.tvName)


            cardRowBinding.imageView.setOnClickListener {
                val pos = adapterPosition

                // Remove item from Adapter List and notify
                mArrayList.removeAt(pos)
                notifyItemRemoved(pos)

                // Remove Object from Original Data from SearchFilter Class
                if (mSearchFilter != null) {
                    mSearchFilter!!.updateList(androidVersion)
                }
            }

            cardRowBinding.root.setOnClickListener {

                listener(androidVersion)
            }

            cardRowBinding.checkBox.setOnCheckedChangeListener(null)
            cardRowBinding.checkBox.isChecked = selected.contains(androidVersion)

            cardRowBinding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                //set your object's last status
                if (isChecked) {
                    checkListener(androidVersion)
                    selected.add(androidVersion)
                } else {
                    unCheckListener(androidVersion)
                    selected.remove(androidVersion)
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
            val searchString = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
            mSearchTerm = searchString
            val results = FilterResults()
            if (TextUtils.isEmpty(searchString)) {
                results.values = listToFilter
            } else {
                val filteredList: MutableList<DataModel> = ArrayList()
                for (dm in listToFilter) {
                    if (dm.name.toLowerCase(Locale.ROOT).contains(searchString)) {
                        filteredList.add(dm)
                    }
                }
                results.values = filteredList
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            mArrayList = results.values as ArrayList<DataModel>
            notifyDataSetChanged()
        }
    }

}