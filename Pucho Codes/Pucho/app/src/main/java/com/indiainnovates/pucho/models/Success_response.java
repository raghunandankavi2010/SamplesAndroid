package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 28-02-2016.
 */
public class Success_response implements Parcelable
{
    private SearchSuccess success;

    protected Success_response(Parcel in) {
        this.success = in.readParcelable(SearchSuccess.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(success, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Success_response> CREATOR = new Creator<Success_response>() {
        @Override
        public Success_response createFromParcel(Parcel in) {
            return new Success_response(in);
        }

        @Override
        public Success_response[] newArray(int size) {
            return new Success_response[size];
        }
    };

    public SearchSuccess getSuccess ()
    {
        return success;
    }

    public void setSuccess (SearchSuccess success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [success = "+success+"]";
    }
}