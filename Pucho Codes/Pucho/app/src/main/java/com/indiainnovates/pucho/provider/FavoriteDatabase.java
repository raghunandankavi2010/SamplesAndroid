package com.indiainnovates.pucho.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Raghunandan on 10-02-2016.
 */
public class FavoriteDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "favoritequestions.db";
    private static final int DB_VERSION = 1;



    public FavoriteDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + FavoriteContract.Favorite.TABLE_FAVORITES + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + FavoriteContract.Favorite.QUESTION_ID + " INTEGER NOT NULL,"
                + FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME + " TEXT NOT NULL,"
                + FavoriteContract.Favorite.QUESTION_ACTIVE + " TEXT,"
                + FavoriteContract.Favorite.QUESTION_TITLE + " TEXT,"
                + FavoriteContract.Favorite.QUESTION_CONTENT + " TEXT,"
                + FavoriteContract.Favorite.QUESTION_UPVOTE + " INTEGER,"
                + FavoriteContract.Favorite.QUESTION_DOWNVOTE + " INTEGER,"
                + FavoriteContract.Favorite.QUESTION_AUDIO_FILE_URL + " TEXT,"
                + FavoriteContract.Favorite.QUESTION_ASKEDON + " TEXT,"
                +FavoriteContract.Favorite.QUESTION_SAVED + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" +FavoriteContract.Favorite.QUESTION_ID + ") ON CONFLICT REPLACE)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DB_NAME);
    }

}
