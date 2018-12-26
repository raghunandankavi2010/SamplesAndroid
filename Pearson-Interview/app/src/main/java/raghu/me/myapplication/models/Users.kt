package raghu.me.myapplication.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Users(

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("username")
    @Expose
    var username: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("address")
    @Expose
    var address: Address? = null,
    @SerializedName("phone")
    @Expose
    var phone: String? = null,
    @SerializedName("website")
    @Expose
    var website: String? = null,
    @SerializedName("company")
    @Expose
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


