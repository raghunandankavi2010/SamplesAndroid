package raghu.me.myapplication.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import raghu.me.myapplication.R
import raghu.me.myapplication.databinding.RowListBinding
import raghu.me.myapplication.models.Users

import java.util.ArrayList

class ListAdapter(context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private val mList = ArrayList<Users>()
    private val onClickListener: OnClickListener

    init {
        onClickListener = context as OnClickListener
    }

    interface OnClickListener {
        fun onClick(user: Users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RowListBinding>(layoutInflater, R.layout.row_list, parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.user = mList[position]
        holder.binding.rootLayout.tag = position
        holder.binding.rootLayout.setOnClickListener { v ->
            val pos = v.tag as Int
            onClickListener.onClick(mList[pos])
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: List<Users>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RowListBinding) : RecyclerView.ViewHolder(binding.root)
}
