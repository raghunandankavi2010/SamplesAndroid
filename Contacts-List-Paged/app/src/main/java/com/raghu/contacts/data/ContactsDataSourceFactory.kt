package com.raghu.contacts.data

import android.content.ContentResolver
import androidx.paging.DataSource

class ContactsDataSourceFactory(private val contentResolver: ContentResolver) :
        DataSource.Factory<Int, Contact>() {

    override fun create(): DataSource<Int, Contact> {
        return ContactsDataSource(contentResolver)
    }
}