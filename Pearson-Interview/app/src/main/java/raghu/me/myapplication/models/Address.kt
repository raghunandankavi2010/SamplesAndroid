package raghu.me.myapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address(
    @SerializedName("street")
    @Expose
    var street: String? = null,
    @SerializedName("suite")
    @Expose
    var suite: String? = null,
    @SerializedName("city")
    @Expose
    var city: String? = null,
    @SerializedName("zipcode")
    @Expose
    var zipcode: String? = null,
    @SerializedName("geo")
    @Expose
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
