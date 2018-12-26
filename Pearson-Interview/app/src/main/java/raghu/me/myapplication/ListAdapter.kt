package raghu.me.myapplication

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import raghu.me.myapplication.ListAdapter.MyViewHolder
import raghu.me.myapplication.model.Users

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
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_list, parent, false) as View

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = mList[position].name
        holder.emailId.text = mList[position].email
        holder.rootView.tag = position
        holder.rootView.setOnClickListener { v ->
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

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var name: TextView
        var emailId: TextView
        val rootView: View

        init {
            name = v.findViewById(R.id.name)
            emailId = v.findViewById(R.id.email)
            rootView = v.findViewById(R.id.rootLayout)


        }
    }


}
