package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geo(
    @SerializedName("lat")
    @Expose
    var lat: String? = null,
    @SerializedName("lng")
    @Expose
    var lng: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(lat)
        writeString(lng)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Geo> = object : Parcelable.Creator<Geo> {
            override fun createFromParcel(source: Parcel): Geo = Geo(source)
            override fun newArray(size: Int): Array<Geo?> = arrayOfNulls(size)
        }
    }
}





