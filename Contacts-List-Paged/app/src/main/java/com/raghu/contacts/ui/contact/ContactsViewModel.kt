package com.raghu.contacts.ui.contact


import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raghu.contacts.data.Contact
import com.raghu.contacts.data.ContactsDataSourceFactory


class ContactsViewModel(private val contentResolver: ContentResolver) : ViewModel() {

    lateinit var contactsList: LiveData<PagedList<Contact>>

    fun loadContacts() {
        val config = PagedList.Config.Builder()
                .setPrefetchDistance(5)
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(false)
                .build()
        contactsList = LivePagedListBuilder<Int, Contact>(
                ContactsDataSourceFactory(contentResolver), config).build()
    }
}
