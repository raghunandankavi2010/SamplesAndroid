package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("catchPhrase")
    @Expose
    var catchPhrase: String? = null,
    @SerializedName("bs")
    @Expose
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


