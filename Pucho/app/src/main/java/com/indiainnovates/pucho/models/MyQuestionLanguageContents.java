package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 19-04-2016.
 */
public class MyQuestionLanguageContents implements Parcelable {

    private boolean active;
    private int id;
    private int questionId;
    private String language;
    private String content;

    protected MyQuestionLanguageContents(Parcel in) {
        active = in.readByte() != 0;
        id = in.readInt();
        questionId = in.readInt();
        language = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeInt(id);
        dest.writeInt(questionId);
        dest.writeString(language);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MyQuestionLanguageContents> CREATOR = new Parcelable.Creator<MyQuestionLanguageContents>() {
        @Override
        public MyQuestionLanguageContents createFromParcel(Parcel in) {
            return new MyQuestionLanguageContents(in);
        }

        @Override
        public MyQuestionLanguageContents[] newArray(int size) {
            return new MyQuestionLanguageContents[size];
        }
    };

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

