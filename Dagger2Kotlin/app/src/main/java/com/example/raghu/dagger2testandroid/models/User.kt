package com.example.raghu.dagger2testandroid.models

import android.os.Parcel
import com.example.raghu.dagger2testandroid.utils.KParcelable
import com.example.raghu.dagger2testandroid.utils.parcelableCreator

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by raghu on 5/8/17.
 */

data class User( @SerializedName("name")
                 @Expose
                 var name: String? = null,
                 @SerializedName("age")
                 @Expose
                 var age: String? = null)  : KParcelable {


    companion object {
        @JvmField val CREATOR = parcelableCreator(::User)

    }

    constructor(source: Parcel) : this(source.readString(), source.readString())
    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) =with(dest) {
        writeString(name)
        writeString(age)
    }
}
