package raghu.me.myapplication.models


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Users(

    @Json(name= "id")
    var id: Int? = null,
    @Json(name= "name")
    var name: String? = null,
    @Json(name= "username")
    var username: String? = null,
    @Json(name= "email")
    var email: String? = null,
    @Json(name= "address")
    var address: Address? = null,
    @Json(name= "phone")
    var phone: String? = null,
    @Json(name= "website")
    var website: String? = null,
    @Json(name= "company")
    var company: Company? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Address>(Address::class.java.classLoader),
        source.readString(),
        source.readString(),
        source.readParcelable<Company>(Company::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(name)
        writeString(username)
        writeString(email)
        writeParcelable(address, 0)
        writeString(phone)
        writeString(website)
        writeParcelable(company, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Users> = object : Parcelable.Creator<Users> {
            override fun createFromParcel(source: Parcel): Users = Users(source)
            override fun newArray(size: Int): Array<Users?> = arrayOfNulls(size)
        }
    }
}


