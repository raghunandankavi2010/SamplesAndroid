package com.raghu.contacts.ui.contact

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghu.contacts.R
import com.raghu.contacts.data.Contact
import com.raghu.contacts.ui.DetailActivity
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.architecture.ext.viewModel


class ContactsFragment : Fragment() {

    private val viewModel by viewModel<ContactsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        contactsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                false)
        val decoration = SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.space))
        contactsList.addItemDecoration(decoration)

        val diffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }
        }

        val adapter = ContactsAdapter(diffCallback) { outer->
            val intent = Intent(activity,DetailActivity::class.java)
            intent.let {
                it.putExtra("name",outer.name)
                //it.putExtra("phoneNumber",outer.phoneNumber)
                startActivity(it)
            }

        }
        contactsList.adapter = adapter

        viewModel.loadContacts()
        viewModel.contactsList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}