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
) : KParcelable {

    private constructor(p: Parcel) : this(
        name = p.readString(),
        catchPhrase = p.readString(),
        bs = p.readString())


    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(catchPhrase)
        writeString(bs)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Company)
    }

}


