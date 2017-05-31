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

package com.example.raghu.phonebook.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

import com.example.raghu.phonebook.model.Contact

@Entity(tableName = "contacts")
class ContactEntity : Contact, Parcelable {

    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0

    override lateinit var firstName: String

    override lateinit var lastName: String

    override lateinit var number: String

    override lateinit var nickName: String

    constructor() {

    }

    constructor(contact: Contact) {
        id = contact.id
        firstName = contact.firstName
        lastName = contact.lastName
        number = contact.number
        nickName = contact.nickName
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ContactEntity> = object : Parcelable.Creator<ContactEntity> {
            override fun createFromParcel(source: Parcel): ContactEntity = ContactEntity(source)
            override fun newArray(size: Int): Array<ContactEntity?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {}
}
