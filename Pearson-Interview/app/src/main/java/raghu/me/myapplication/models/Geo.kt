package raghu.me.myapplication.models

import android.os.Parcel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geo(
    @Json(name = "lat")
    var lat: String? = null,
    @Json(name = "lng")
    var lng: String? = null
) : KParcelable {


    private constructor(p: Parcel) : this(
        lat = p.readString(),
        lng = p.readString()
    )


    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(lat)
        writeString(lng)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Geo)
    }
}





