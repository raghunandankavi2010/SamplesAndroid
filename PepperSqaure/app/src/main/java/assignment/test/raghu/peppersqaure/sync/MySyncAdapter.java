package assignment.test.raghu.peppersqaure.sync;

/**
 * Created by Raghunandan on 22-11-2015.
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import assignment.test.raghu.peppersqaure.R;
import assignment.test.raghu.peppersqaure.Utilities.Utils;
import assignment.test.raghu.peppersqaure.models.Actors;
import assignment.test.raghu.peppersqaure.models.ActorsList;
import assignment.test.raghu.peppersqaure.provider.ActorContract;


public class MySyncAdapter extends AbstractThreadedSyncAdapter {



    private static final String[] PROJECTION = new String[]{
            BaseColumns._ID,
            ActorContract.Actors.ACTOR_ID,
            ActorContract.Actors.ACTOR_NAME,
            ActorContract.Actors.ACTOR_DESCRIPTION,
            ActorContract.Actors.ACTOR_DOB,
            ActorContract.Actors.ACTOR_COUNTRY,
            ActorContract.Actors.ACTOR_HEIGHT,
            ActorContract.Actors.ACTOR_SPOUSE,
            ActorContract.Actors.ACTOR_CHILDREN,
            ActorContract.Actors.ACTOR_IMAGE,
    };


    // Constants representing column positions from PROJECTION.

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_ACTOR_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_DESCRIPTION = 3;
    public static final int COLUMN_DOB = 4;
    public static final int COLUMN_COUNTRY = 5;
    public static final int COLUMN_HEIGHT = 6;
    public static final int COLUMN_SPOUSE = 7;
    public static final int COLUMN_CHILDREN = 8;
    public static final int COLUMN_IMAGE = 9;
    public static final int COLUMN_FAV = 10;


    public static final String TAG = MySyncAdapter.class.getSimpleName();

    /**
     * URL to fetch content from during a sync.
     * <p/>
     * <p>This points to the Android Developers Blog. (Side note: We highly recommend reading the
     * Android Developer Blog to stay up to date on the latest Android platform developments!)
     */
    private static final String FEED_URL = "https://api.myjson.com/bins/3orxx";

    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

    private ContentResolver mContentResolver;

    public MySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        Log.i(TAG, "Beginning network synchronization");
        try {
            final URL location = new URL(FEED_URL);
            InputStream stream = null;

            try {
                Log.i(TAG, "Streaming data from network: " + location);
                stream = downloadUrl(location);
                updateLocalFeedData(stream, syncResult);
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Feed URL is malfo}rmed", e);
            syncResult.stats.numParseExceptions++;
            return;
        }
        Log.i(TAG, "Network synchronization complete");
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        //ContentResolver.requestSync(getSyncAccount(context), ActorContract.CONTENT_AUTHORITY, bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.account_type));

        // If the password doesn't exist, the account doesn't exist
            if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

        }
        return newAccount;
    }


    /**
     * Read XML from an input stream, storing it into the content provider.
     * <p/>
     * <p>This is where incoming data is persisted, committing the results of a sync. In order to
     * minimize (expensive) disk operations, we compare incoming data with what's already in our
     * database, and compute a merge. Only changes (insert/update/delete) will result in a database
     * write.
     * <p/>
     * <p>As an additional optimization, we use a batch operation to perform all database writes at
     * once.
     * <p/>
     * <p>Merge strategy:
     * 1. Get cursor to all items in feed<br/>
     * 2. For each item, check if it's in the incoming data.<br/>
     * a. YES: Remove from "incoming" list. Check if data has mutated, if so, perform
     * database UPDATE.<br/>
     * b. NO: Schedule DELETE from database.<br/>
     * (At this point, incoming database only contains missing items.)<br/>
     * 3. For any items remaining in incoming list, ADD to database.
     */
    public void updateLocalFeedData(final InputStream stream, final SyncResult syncResult)
            throws IOException, JSONException, RemoteException,
            OperationApplicationException, ParseException {

        Log.i(TAG, "Parsing Json");
        String response = Utils.getStringFromInputStream(stream);

        Gson gson = new Gson();
        ActorsList responseModel = gson.fromJson(response, ActorsList.class);

        ArrayList<Actors> moviesList = responseModel.getActors();
        final ContentResolver contentResolver = getContext().getContentResolver();


        Log.i(TAG, "Parsing complete. Found " + moviesList.size() + " entries");


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // Build hash table of incoming entries
        HashMap<Integer, Actors> moviesMap = new HashMap<Integer, Actors>();
        for (Actors e : moviesList) {
            moviesMap.put(e.getId(), e);
        }

        // Get list of all items
        Log.i(TAG, "Fetching local entries for merge");
        Uri uri = ActorContract.Actors.CONTENT_URI; // Get all entries
        Cursor c = contentResolver.query(uri, PROJECTION, null, null, null);
        assert c != null;
        if (c != null) {
            Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");

            // Find stale data
            int  favoured,id,actorid;
            float average;
            String name, description, dob, country, height, spouse, children, image;

            while (c.moveToNext()) {
                syncResult.stats.numEntries++;
                id = c.getInt(COLUMN_ID);
                actorid = c.getInt(COLUMN_ACTOR_ID);
                name = c.getString(COLUMN_NAME);
                description = c.getString(COLUMN_DESCRIPTION);
                dob = c.getString(COLUMN_DOB);
                country = c.getString(COLUMN_COUNTRY);
                height = c.getString(COLUMN_HEIGHT);
                spouse = c.getString(COLUMN_SPOUSE);
                children = c.getString(COLUMN_CHILDREN);
                image = c.getString(COLUMN_IMAGE);
                favoured = c.getInt(COLUMN_FAV);

                Actors match = moviesMap.get(actorid);
                if (match != null) {
                    // Entry exists. Remove from entry map to prevent insert later.
                    moviesMap.remove(name);
                /* Check to see if the entry needs to be updated */
                    Uri existingUri = ActorContract.Actors.CONTENT_URI.buildUpon()
                            .appendPath(Integer.toString(id)).build();

                        // Update existing record
                        Log.i(TAG, "Scheduling update: " + existingUri);
                        batch.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValue(ActorContract.Actors.ACTOR_ID, match.getId())
                                .withValue(ActorContract.Actors.ACTOR_NAME, match.getName())
                                .withValue(ActorContract.Actors.ACTOR_DESCRIPTION, match.getDescription())
                                .withValue(ActorContract.Actors.ACTOR_DOB, match.getDob())
                                .withValue(ActorContract.Actors.ACTOR_COUNTRY, match.getCountry())
                                .withValue(ActorContract.Actors.ACTOR_HEIGHT, match.getHeight())
                                .withValue(ActorContract.Actors.ACTOR_SPOUSE, match.getSpouse())
                                .withValue(ActorContract.Actors.ACTOR_CHILDREN, match.getChildren())
                                .withValue(ActorContract.Actors.ACTOR_IMAGE, match.getImage())
                                .withValue(ActorContract.Actors.ACTOR_FAV, match.getFav())
                                .build());
                        syncResult.stats.numUpdates++;

                } else {
                    // Entry doesn't exist. Remove it from the database.
                    Uri deleteUri = ActorContract.Actors.CONTENT_URI.buildUpon()
                            .appendPath(Integer.toString(id)).build();
                    Log.i(TAG, "Scheduling delete: " + deleteUri);
                    batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            }
            c.close();
        }


        // Add new items
        for (Actors e : moviesMap.values()) {
            Log.i(TAG, "Scheduling insert: actorname=" + e.getName());
            batch.add(ContentProviderOperation.newInsert(ActorContract.Actors.CONTENT_URI)
                    .withValue(ActorContract.Actors.ACTOR_ID, e.getId())
                    .withValue(ActorContract.Actors.ACTOR_NAME, e.getName())
                    .withValue(ActorContract.Actors.ACTOR_DESCRIPTION, e.getDescription())
                    .withValue(ActorContract.Actors.ACTOR_DOB, e.getDob())
                    .withValue(ActorContract.Actors.ACTOR_COUNTRY, e.getCountry())
                    .withValue(ActorContract.Actors.ACTOR_HEIGHT, e.getHeight())
                    .withValue(ActorContract.Actors.ACTOR_SPOUSE, e.getSpouse())
                    .withValue(ActorContract.Actors.ACTOR_CHILDREN, e.getChildren())
                    .withValue(ActorContract.Actors.ACTOR_IMAGE, e.getImage())
                    .withValue(ActorContract.Actors.ACTOR_FAV, e.getFav())
                    .build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        mContentResolver.applyBatch(ActorContract.CONTENT_AUTHORITY, batch);
        mContentResolver.notifyChange(
                ActorContract.Actors.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);                         // IMPORTANT: Do not sync to network
        // This sample doesn't support uploads, but if *your* code does, make sure you set
        // syncToNetwork=false in the line above to prevent duplicate syncs.
    }


    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    private InputStream downloadUrl(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
        conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}
