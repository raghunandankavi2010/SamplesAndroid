package com.indiainnovates.pucho.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Raghunandan on 10-02-2016.
 */
public class FavoriteProvider extends ContentProvider {

    private static final String TAG= FavoriteProvider.class.getSimpleName();


    /**
     * Content authority for this provider.
     */
    private static final String AUTHORITY = FavoriteContract.CONTENT_AUTHORITY;

    private static final int FAVORITES = 1;

    private FavoriteDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI(AUTHORITY, "favorites", FAVORITES);

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FavoriteDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {

            case FAVORITES:
                // Return all known entries.
                builder.table(FavoriteContract.Favorite.TABLE_FAVORITES)
                        .where(selection, selectionArgs);
                Cursor c = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                Context ctx = getContext();
                assert ctx != null;
                c.setNotificationUri(ctx.getContentResolver(), uri);
                return c;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                return FavoriteContract.Favorite.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {


        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES: {

                db.insertOrThrow(FavoriteContract.Favorite.TABLE_FAVORITES, null, contentValues);
                notifyChange(uri);
                return FavoriteContract.Favorite.buildFavoriteUri(contentValues.getAsString(FavoriteContract.Favorite.QUESTION_ID));
            }

            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

    private void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if (uri.equals(FavoriteContract.BASE_CONTENT_URI)) {
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case FAVORITES:
                count = builder.table(FavoriteContract.Favorite.TABLE_FAVORITES)
                        .where(selection, selectionArgs)
                        .update(db, contentValues);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        FavoriteDatabase.deleteDatabase(context);
        mOpenHelper = new FavoriteDatabase(getContext());
    }

    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES: {
                return builder.table(FavoriteContract.Favorite.TABLE_FAVORITES);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }

}
