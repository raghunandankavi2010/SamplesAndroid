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

package com.example.raghu.phonebook.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.raghu.phonebook.model.Contact;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "contacts")
public class ContactEntity implements Contact, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstName;
    private String lastName;
    private int number;
    private String nickName;

    public ContactEntity() {

    }
    protected ContactEntity(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        number = in.readInt();
        nickName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(number);
        dest.writeString(nickName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactEntity> CREATOR = new Creator<ContactEntity>() {
        @Override
        public ContactEntity createFromParcel(Parcel in) {
            return new ContactEntity(in);
        }

        @Override
        public ContactEntity[] newArray(int size) {
            return new ContactEntity[size];
        }
    };

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ContactEntity(Contact contact) {
        id = contact.getId();
        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        number  = contact.getNumber();
        nickName = contact.getNickName();
    }

    @NotNull
    @Override
    public String getFirstName() {
        return firstName;
    }

    @NotNull
    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @NotNull
    @Override
    public String getNickName() {
        return nickName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
