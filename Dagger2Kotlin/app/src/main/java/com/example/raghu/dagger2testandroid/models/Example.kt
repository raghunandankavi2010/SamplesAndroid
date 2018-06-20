package com.example.raghu.dagger2testandroid.models

/**
 * Created by raghu on 5/8/17.
 */

import android.os.Parcel
import android.os.Parcelable
import com.example.raghu.dagger2testandroid.PostProcessable
import com.example.raghu.dagger2testandroid.utils.*

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Example(@SerializedName("user") @Expose var user: User? = null, var isExample: Boolean =false) : KParcelable, PostProcessable {
    override fun gsonPostProcess() {
        isExample = true

    }

    constructor(source: Parcel) : this(
            source.readTypedObjectCompat(User.CREATOR),
            //source.readParcelable<User>(User::class.java.classLoader),
            source.readBoolean()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        writeTypedObjectCompat(user, flags)
        writeBoolean(isExample)
    }


    companion object {
        @JvmField
        val CREATOR = parcelableCreatorTry({
            Example(it)
        })

    }


}



