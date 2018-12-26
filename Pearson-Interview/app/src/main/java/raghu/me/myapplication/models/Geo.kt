package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geo(
    @Json(name= "lat")
    var lat: String? = null,
    @Json(name= "lng")
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





