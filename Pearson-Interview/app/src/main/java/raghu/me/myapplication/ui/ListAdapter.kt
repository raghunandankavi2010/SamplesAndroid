package raghu.me.myapplication.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import raghu.me.myapplication.AppExecutors
import raghu.me.myapplication.R
import raghu.me.myapplication.databinding.RowListBinding
import raghu.me.myapplication.models.Users
import raghu.me.myapplication.ui.common.DataBoundListAdapter

import java.util.ArrayList

/*class ListAdapter(context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

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
}*/

class ListAdapter(
    private val dataBindingComponent: DataBindingComponent, appExecutors: AppExecutors,mContext: Context) : DataBoundListAdapter<Users, RowListBinding>(
    context = mContext,
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.address == newItem.address
                    && oldItem.company == newItem.company
        }
    }
)
{
    private val onClickListener: OnClickListener
    interface OnClickListener {
        fun onClick(user: Users)
    }

    init {
        onClickListener = mContext as OnClickListener
    }


    override fun createBinding(parent: ViewGroup): RowListBinding {
        val binding = DataBindingUtil
            .inflate<RowListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_list,
                parent,
                false,
                dataBindingComponent
            )
       binding.rootLayout.setOnClickListener { v ->
           binding.user?.let {
               onClickListener.onClick(it)
           }
        }

        return binding
    }

    override fun bind(binding: RowListBinding, item: Users) {
        binding.user = item
    }
}

