package com.raghu.contacts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_detail.*
import android.provider.ContactsContract.CommonDataKinds.Phone

import android.provider.ContactsContract
import com.raghu.contacts.R


class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val username = intent.getStringExtra("name")
        name.text = username

        val cr = contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                "DISPLAY_NAME = '$username'", null, null)
        if (cursor!!.moveToFirst()) {
            val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            //
            //  Get all phone numbers.
            //
            val phones = cr.query(Phone.CONTENT_URI, null,
                    Phone.CONTACT_ID + " = " + contactId, null, null)
            while (phones!!.moveToNext()) {
                val phnumber = phones.getString(phones.getColumnIndex(Phone.NUMBER))
               number.text = phnumber

            }
            phones.close()
        }
        cursor.close()
    }
}