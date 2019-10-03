package com.raghu.contacts.ui.contact

import android.content.Context
import android.provider.ContactsContract
import androidx.paging.PagedList
import com.raghu.contacts.data.Contact

class ContactsLiveData(
        context: Context
) : ContentProviderLiveData<PagedList<Contact>>(context, uri) {

    override fun getContentProviderValue(): PagedList<Contact> {
        // TODO: query your repository and generate the list of contact's name for example
        return emptyList()
    }

    companion object {
        /**
         * Uri used for this [LiveData].
         */
        private val uri =  ContactsContract.Contacts.CONTENT_URI
    }
}