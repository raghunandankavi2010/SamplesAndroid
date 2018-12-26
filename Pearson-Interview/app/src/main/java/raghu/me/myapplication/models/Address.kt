package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    @Json(name= "street")
    var street: String? = null,
    @Json(name= "suite")
    var suite: String? = null,
    @Json(name= "city")
    var city: String? = null,
    @Json(name= "zipcode")
    var zipcode: String? = null,
    @Json(name= "geo")
    var geo: Geo? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Geo>(Geo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(street)
        writeString(suite)
        writeString(city)
        writeString(zipcode)
        writeParcelable(geo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Address> = object : Parcelable.Creator<Address> {
            override fun createFromParcel(source: Parcel): Address = Address(source)
            override fun newArray(size: Int): Array<Address?> = arrayOfNulls(size)
        }
    }
}
