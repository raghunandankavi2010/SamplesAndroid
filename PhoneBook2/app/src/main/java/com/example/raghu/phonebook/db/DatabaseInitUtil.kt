/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.raghu.phonebook.db


import com.example.raghu.phonebook.entity.ContactEntity
import java.util.*
import kotlin.collections.ArrayList


/** Generates dummy data and inserts them into the database  */
class DatabaseInitUtil {


    companion object Factory {
        fun initializeDb(db: AppDatabase) {
            val contacts = generateData(mutableListOf<ContactEntity>())
            insertData(db, contacts)

        }

        fun generateData(contacts: MutableList<ContactEntity>): List<ContactEntity> {


            for (i in 0..9) {
                val contact = ContactEntity()
                contact.setFirstName("name")
                contact.setLastName("lastname")
                contact.setNumber(9)
                contact.setNickName("nickname")
                contacts.add(contact)
            }
            return contacts
        }


        fun insertData(db: AppDatabase, contacts: List<ContactEntity>) {
            db.beginTransaction()
            try {
                db.contactDao().insertAll(contacts)
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }
    }
}


