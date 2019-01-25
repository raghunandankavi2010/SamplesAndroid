package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(@Json(name= "street")
              var street: String? = null,
              @Json(name= "suite")
              var suite: String? = null,
              @Json(name= "city")
              var city: String? = null,
              @Json(name= "zipcode")
              var zipcode: String? = null,
              @Json(name= "geo")
              var geo: Geo? = null) : KParcelable {

    private constructor(p: Parcel) : this(
        street = p.readString(),
        suite = p.readString(),
        city = p.readString(),
        zipcode = p.readString(),
        geo = p.readTypedObjectCompat(Geo.CREATOR))

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(street)
        writeString(suite)
        writeString(city)
        writeString(zipcode)
        writeTypedObjectCompat(geo, flags)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Address)
    }
}
