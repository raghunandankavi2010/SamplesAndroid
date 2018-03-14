package com.example.raghu.dagger2testandroid.models

/**
 * Created by raghu on 5/8/17.
 */

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


 data class Example(@SerializedName("user")
                    @Expose
                    var user: User)  : Parcelable {


    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Example> = object : Parcelable.Creator<Example> {
            override fun createFromParcel(source: Parcel): Example = Example(source)
            override fun newArray(size: Int): Array<Example?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(

            user = source.readParcelable<User>(User::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(user,flags)
    }
}