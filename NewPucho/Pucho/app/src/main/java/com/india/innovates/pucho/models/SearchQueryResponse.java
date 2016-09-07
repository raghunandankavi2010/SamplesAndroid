package com.india.innovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 27-02-2016.
 */
public class SearchQueryResponse implements Parcelable
{
    private Success_response success_response;

    private boolean success;

    public Success_response getSuccess_response ()
    {
        return success_response;
    }

    public void setSuccess_response (Success_response success_response)
    {
        this.success_response = success_response;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess (boolean success)
    {
        this.success = success;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isSuccess() ? 1 : 0));
        dest.writeParcelable(success_response, flags);

    }

    public SearchQueryResponse(Parcel in) {

        this.success =  in.readByte() != 0;
        this.success_response =  in.readParcelable(Success_response.class.getClassLoader());

    }

    public static final Creator<SearchQueryResponse> CREATOR = new Creator<SearchQueryResponse>() {
        public SearchQueryResponse createFromParcel(Parcel source) {
            return new SearchQueryResponse(source);
        }

        public SearchQueryResponse[] newArray(int size) {
            return new SearchQueryResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
