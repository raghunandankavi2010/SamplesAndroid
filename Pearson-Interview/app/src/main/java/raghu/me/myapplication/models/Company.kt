package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Company(
    @Json(name= "name")
    var name: String? = null,
    @Json(name= "catchPhrase")
    var catchPhrase: String? = null,
    @Json(name= "bs")
    var bs: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(catchPhrase)
        writeString(bs)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Company> = object : Parcelable.Creator<Company> {
            override fun createFromParcel(source: Parcel): Company = Company(source)
            override fun newArray(size: Int): Array<Company?> = arrayOfNulls(size)
        }
    }
}


