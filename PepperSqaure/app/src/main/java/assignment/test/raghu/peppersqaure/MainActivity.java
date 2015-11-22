package assignment.test.raghu.peppersqaure;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import assignment.test.raghu.peppersqaure.Utilities.SyncUtils;
import assignment.test.raghu.peppersqaure.adapter.MyCursorAdapter;
import assignment.test.raghu.peppersqaure.provider.ActorContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, MyCursorAdapter.OnFavClickListener {

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
            ActorContract.Actors.ACTOR_FAV
    };

    private MyCursorAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Cursor mCursor;

    private Object mSyncObserverHandle;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MyCursorAdapter(this, mCursor);
        mRecyclerView.setAdapter(mAdapter);

        SyncUtils.CreateSyncAccount(this);
        getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,  // Context
                ActorContract.Actors.CONTENT_URI, // URI
                PROJECTION,                // Projection
                null,                           // Selection
                null,                           // Selection args
                null); // Sort

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver() {
        /** Callback invoked with the sync adapter status changes. */
        @Override
        public void onStatusChanged(int which) {
            runOnUiThread(new Runnable() {
                /**
                 * The SyncAdapter runs on a background thread. To update the UI, onStatusChanged()
                 * runs on the UI thread.
                 */
                @Override
                public void run() {

                    AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);

                    // Create the account type and default account
                    Account account = new Account(getString(R.string.app_name), getString(R.string.account_type));

                    if (account == null) {
                        // GetAccount() returned an invalid value. This shouldn't happen, but
                        // we'll set the status to "not refreshing".
                        //setRefreshActionButtonState(false);
                        check(false);
                        return;
                    }

                    // Test the ContentResolver to see if the sync adapter is active or pending.
                    // Set the state of the refresh button accordingly.
                    boolean syncActive = ContentResolver.isSyncActive(
                            account, ActorContract.CONTENT_AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(
                            account, ActorContract.CONTENT_AUTHORITY);
                    check(syncActive || syncPending);
                    // setRefreshActionButtonState(syncActive || syncPending);
                }
            });
        }
    };

    public void check(boolean bool) {
        if (bool) {
            //displayProgressBar();

        } else {
            //hideProgressBar();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mSyncStatusObserver.onStatusChanged(0);

        // Watch for sync state changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask, mSyncStatusObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSyncObserverHandle != null) {
            ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
            mSyncObserverHandle = null;
        }
    }

    @Override
    public void onFavClick(Cursor cursor) {


        int fav = cursor.getInt(cursor.getColumnIndex(ActorContract.Actors.ACTOR_FAV));

        ContentValues values = new ContentValues();
        if (fav == 1) {
            values.put(ActorContract.Actors.ACTOR_FAV, 0);
        } else if (fav == 0) {
            values.put(ActorContract.Actors.ACTOR_FAV, 1);



        }
        contentResolver.update(ActorContract.Actors.CONTENT_URI,
                values, ActorContract.Actors.ACTOR_ID + "=?",
                new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(ActorContract.Actors.ACTOR_ID)))}); //id is the id of the row you wan to update
        //getSupportLoaderManager().restartLoader(0,null,this);
        contentResolver.notifyChange(
                ActorContract.Actors.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);
    }
}

