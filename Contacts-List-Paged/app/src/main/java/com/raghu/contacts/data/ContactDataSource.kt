package com.raghu.contacts.data

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.paging.PositionalDataSource
import timber.log.Timber


class ContactsDataSource(private val contentResolver: ContentResolver) :
        PositionalDataSource<Contact>() {

    companion object {
        private val PROJECTION = arrayOf(
        ContactsContract.Profile._ID,
        ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
        ContactsContract.Profile.LOOKUP_KEY,
        ContactsContract.Profile.PHOTO_THUMBNAIL_URI
        )
    }
/*    companion object {
        private val PROJECTION = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI

        )
    }*/


    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Contact>) {
        callback.onResult(getContacts(params.requestedLoadSize, params.requestedStartPosition), 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Contact>) {
        callback.onResult(getContacts(params.loadSize, params.startPosition))
    }

    private fun getContacts(limit: Int, offset: Int): MutableList<Contact> {
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY +
                        " ASC LIMIT " + limit + " OFFSET " + offset)

        cursor?.moveToFirst()
        val contacts: MutableList<Contact> = mutableListOf()
        cursor?.let {
            while (!it.isAfterLast) {
                val id = it.getLong(it.getColumnIndex(PROJECTION[0]))
                var name = it.getString(it.getColumnIndex(PROJECTION[1]))
                var photoUri = it.getString(it.getColumnIndex(PROJECTION[3]))
                val lookupKey = it.getString(it.getColumnIndex(PROJECTION[2]))
               // var phoneNumber = getPhone(contentResolver, it.getString(
                 //       it.getColumnIndex(ContactsContract.Contacts._ID)))
                if (name == null) {
                    name = ""
                }
                if (photoUri == null) {
                    photoUri = ""
                }
                //if (phoneNumber == null) {
               //     phoneNumber = ""
                //}
                Timber.i(String.format("Name : %s", name))
                Timber.i(String.format("Photo : %s", photoUri))

                contacts.add(Contact(id, lookupKey, name, photoUri))
                cursor.moveToNext()
            }
        }
        cursor?.close()

        return contacts
    }

    private fun getPhone(cr: ContentResolver, contactID: String): String? {

        var contactNumber: String? = null
        val cursorPhone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                arrayOf(contactID), null)

        if (cursorPhone != null && cursorPhone!!.moveToFirst()) {
            contactNumber = cursorPhone!!.getString(cursorPhone!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

        }

        if (cursorPhone != null)
            cursorPhone!!.close()

        return contactNumber
    }
}