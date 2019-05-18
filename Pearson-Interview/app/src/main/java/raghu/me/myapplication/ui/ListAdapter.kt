package raghu.me.myapplication.ui

import android.content.Context
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


class ListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    mContext: Context,
    private val callback: ((Users) -> Unit)?
) : DataBoundListAdapter<Users, RowListBinding>(
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
               callback?.invoke(it)
              // onClickListener.onClick(it)
           }
        }

        return binding
    }

    override fun bind(binding: RowListBinding, item: Users) {
        binding.user = item
    }
}

