package com.example.raghu.dagger2testandroid.models

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by raghu on 5/8/17.
 */

class User  : Parcelable {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("age")
    @Expose
    var age: String? = null

    constructor() {}

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {}
}
