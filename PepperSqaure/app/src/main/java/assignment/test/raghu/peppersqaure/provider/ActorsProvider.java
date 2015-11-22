package assignment.test.raghu.peppersqaure.provider;

/**
 * Created by Raghunandan on 22-11-2015.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


/**
 * Created by Raghunandan on 20-10-2015.
 */
public class ActorsProvider extends ContentProvider {

    private static final String TAG = ActorsProvider.class.getSimpleName();


    /**
     * Content authority for this provider.
     */
    private static final String AUTHORITY = ActorContract.CONTENT_AUTHORITY;

    // The constants below represent individual URI routes, as IDs. Every URI pattern recognized by
    // this ContentProvider is defined using sUriMatcher.addURI(), and associated with one of these
    // IDs.
    //
    // When a incoming URI is run through sUriMatcher, it will be tested against the defined
    // URI patterns, and the corresponding route ID will be returned.

    private static final int ACTORS = 1;

    private ActorsDatabase mOpenHelper;


    /**
     * UriMatcher, used to decode incoming URIs.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, "actors", ACTORS);

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ActorsDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();

        // Return all known entries.
        builder.table(ActorContract.Actors.TABLE_MOVIES)
                .where(selection, selectionArgs);
        Cursor c = builder.query(db, projection, sortOrder);
        // Note: Notification URI must be manually set here for loaders to correctly
        // register ContentObservers.
        Context ctx = getContext();
        assert ctx != null;
        c.setNotificationUri(ctx.getContentResolver(), uri);
        return c;


    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        return ActorContract.Actors.CONTENT_TYPE;


    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long id = db.insertOrThrow(ActorContract.Actors.TABLE_MOVIES, null, contentValues);
        notifyChange(uri);

        return ActorContract.Actors.buildActorsUri(contentValues.getAsString(ActorContract.Actors.ACTOR_ID));


    }

    private void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;

        count = builder.table(ActorContract.Actors.TABLE_MOVIES)
                .where(selection, selectionArgs)
                .update(db, contentValues);

        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

}
