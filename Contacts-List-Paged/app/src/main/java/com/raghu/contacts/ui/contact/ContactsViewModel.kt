package com.raghu.contacts.ui.contact


import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.raghu.contacts.data.Contact
import com.raghu.contacts.data.ContactsDataSourceFactory
import timber.log.Timber


class ContactsViewModel(private val contentResolver: ContentResolver) : ViewModel() {

    lateinit var contactsList: LiveData<PagedList<Contact>>

    fun loadContacts() {
        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build()
        contactsList = LivePagedListBuilder<Int, Contact>(
                ContactsDataSourceFactory(contentResolver), config).build()
    }
}
