package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Raghunandan on 28-02-2016.
 */
public class SearchSuccess implements Parcelable{

    private int total;

    private List<SearchQuestions> questions_data;

    protected SearchSuccess(Parcel in) {
        total = in.readInt();
        questions_data = in.createTypedArrayList(SearchQuestions.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeTypedList(questions_data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchSuccess> CREATOR = new Creator<SearchSuccess>() {
        @Override
        public SearchSuccess createFromParcel(Parcel in) {
            return new SearchSuccess(in);
        }

        @Override
        public SearchSuccess[] newArray(int size) {
            return new SearchSuccess[size];
        }
    };

    public int getTotal ()
    {
        return total;
    }

    public void setTotal (int total)
    {
        this.total = total;
    }

    public List<SearchQuestions> getQuestions_data ()
    {
        return questions_data;
    }

    public void setQuestions_data (List<SearchQuestions> questions_data)
    {
        this.questions_data = questions_data;
    }
}
