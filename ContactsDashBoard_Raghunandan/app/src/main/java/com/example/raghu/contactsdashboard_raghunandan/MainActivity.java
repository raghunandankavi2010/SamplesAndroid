package com.example.raghu.contactsdashboard_raghunandan;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends AppCompatActivity implements MainActivityContract.View,CallLogsObserver.CallLogChangeCallback {

    public static final String TAG = "MainActivity";


    private RecyclerView recyclerView;
    private ContactsCallLogAdapter mAdapter;
    private ProgressBar pb;

    private CallLogsObserver callLogsObserver;
    private MainActivityContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb =  (ProgressBar)findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ContactsCallLogAdapter(MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mActionsListener = new MainActivityPresenter(this);
        callLogsObserver = new CallLogsObserver(new Handler(),this);
        MainActivityPermissionsDispatcher.getAllContactsWithCheck(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().
                registerContentObserver(
                        CallLog.Calls.CONTENT_URI,
                        true,
                        callLogsObserver);
    }

    @NeedsPermission(value = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG})
    public void getAllContacts() {

        //test();
        mActionsListener.getDetails(false);

    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    void readContactsOnShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    void readContactsOnPermissionDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    void eadContactsOnNeverAskAgain() {
    }

    @OnShowRationale(Manifest.permission.READ_CALL_LOG)
    void readCallLogOnShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied(Manifest.permission.READ_CALL_LOG)
    void readCallLogOnPermissionDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_CALL_LOG)
    void readCallLogOnNeverAskAgain() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().
                unregisterContentObserver(callLogsObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActionsListener.onDestroy();
    }

    @Override
    public void showProgressBar(boolean show) {
        if(show) {
            pb.setVisibility(View.VISIBLE);
        }else {
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void addItemAdapter(List<Contacts> contacts) {

        mAdapter.setContactsList(contacts);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                mActionsListener.getDetails(true);
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void calllogContentChanged(boolean selfChange, Uri uri) {


        Log.d(TAG,""+selfChange);
        if(selfChange)
        {
            mActionsListener.getDetails(true);
        }
    }


}
