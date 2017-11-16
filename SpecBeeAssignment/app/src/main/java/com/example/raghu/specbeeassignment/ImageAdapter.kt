package com.example.raghu.specbeeassignment

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.raghu.specbeeassignment.databinding.RowItemsBinding
import com.example.raghu.specbeeassignment.databinding.RowTextBinding
import java.util.*


/**
 * Created by raghu on 16/11/17.
 */

class ImageAdapter(private val mContext: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItems = ArrayList<Items>()


    init {
        this.mItems = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup,

                                    viewType: Int): RecyclerView.ViewHolder {


        val viewHolder: RecyclerView.ViewHolder
        if (viewType == VIEW_IMAGE) {
            val itembinding : RowItemsBinding = DataBindingUtil.inflate<RowItemsBinding>(LayoutInflater.from(parent.getContext()), R.layout.row_items, parent, false)

            viewHolder = ViewHolder(itembinding)
        } else {
            val textBinding = DataBindingUtil.inflate<RowTextBinding>(LayoutInflater.from(parent.getContext()), R.layout.row_text, parent, false)



            viewHolder = ViewHolder_Text(textBinding)
        }

        return viewHolder


    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ViewHolder) {

            viewHolder.bind(mItems[position], onItemClickListener)

        } else {
            val mHolder = viewHolder as ViewHolder_Text
            mHolder.bind(mItems[position], onItemClickListener)
        }


    }


    fun add(items: Items) {

        mItems.add(items)

        notifyItemRangeInserted(itemCount, 1)
    }

    class ViewHolder(viewbinding: RowItemsBinding) : RecyclerView.ViewHolder(viewbinding.root) {

        private val binding: RowItemsBinding

        init {

            binding = viewbinding
        }

        fun bind(item: Items, listener: OnItemClickListener) {
            binding.items = item
            binding.root?.setOnClickListener { listener.onItemClick(item.url) }

        }
    }

    class ViewHolder_Text(viewbinding: RowTextBinding) : RecyclerView.ViewHolder(
            viewbinding.root) {
        private val binding: RowTextBinding

        init {
            binding = viewbinding

        }

        fun bind(item: Items, listener: OnItemClickListener) {
            binding.item = item
            binding.root.setOnClickListener { listener.onItemClickText(item.text) }

        }

    }

    override fun getItemCount(): Int {

        return mItems.size
    }


    override fun getItemViewType(position: Int): Int {

        return   if (!TextUtils.isEmpty(mItems[position].url)) VIEW_IMAGE else VIEW_TEXT

    }

    companion object {

        val VIEW_IMAGE = 1
        val VIEW_TEXT = 0
    }
}

