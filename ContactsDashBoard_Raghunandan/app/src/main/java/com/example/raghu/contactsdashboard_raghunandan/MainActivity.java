package com.example.raghu.contactsdashboard_raghunandan;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private List<Contacts> contactsList = new ArrayList<>();

    private CompositeDisposable composite = new CompositeDisposable();

    private RecyclerView recyclerView;
    private ContactsCallLogAdapter mAdapter;
    private ProgressBar pb;

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
        MainActivityPermissionsDispatcher.getAllContactsWithCheck(this);

    }


    @NeedsPermission(value = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG})
    public void getAllContacts() {


        pb.setVisibility(View.VISIBLE);

        DisposableSingleObserver<List<Contacts>> disposableSingleObserver = getObservable().flatMapIterable(new Function<List<Contacts>, Iterable<Contacts>>() {
            @Override
            public Iterable<Contacts> apply(@NonNull List<Contacts> integers) throws Exception {
                return integers;
            }
        })
                .flatMap(new Function<Contacts, ObservableSource<Contacts>>() {
                    @Override
                    public ObservableSource<Contacts> apply(Contacts contacts) throws Exception {
                        return getCallLog(contacts);
                    }
                })
                .filter(new Predicate<Contacts>() {
                    @Override
                    public boolean test(Contacts contact) {
                        if (contact == null)
                            return false;
                        else {
                            return contact.getDuration() > 0;
                        }
                    }
                }).toList()
                .map(new Function<List<Contacts>, List<Contacts>>() {
                    @Override
                    public List<Contacts> apply(@io.reactivex.annotations.NonNull List<Contacts> contacts) throws Exception {
                        return Utils.sort(contacts);
                    }
                })
                .subscribeOn(Schedulers.io())
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Contacts>>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull List<Contacts> contacts) {

                        // Contact Name | Photo | Mobile | Email | Last Contact Time | Total Talk Duration Time (Sort in Descending Order of Total TalkTime)
                        for (Contacts contact : contacts) {
                            Log.d(TAG, "Name : " + contact.getName());
                            Log.d(TAG, "Photo : " + contact.getPhoto());
                            Log.d(TAG, "Number : " + contact.getNumber());
                            Log.d(TAG, "Email : " + contact.getEmail());
                            Log.d(TAG, "Date : " + contact.getCallDate());
                            Log.d(TAG, "Duration : " + Utils.timeConversion(contact.getDuration()));
                            Log.d(TAG, ".........................");
                        }

                        pb.setVisibility(View.GONE);
                        mAdapter.setContactsList(contacts);
                        mAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        e.printStackTrace();
                    }
                });

        composite.add(disposableSingleObserver);

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

    public List<Contacts> readAllContacts() {

        String contactNAME, contactID, contactphotoURI, contactNumber, contactEmail;
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur != null && cur.getCount() > 0) {

            while (cur.moveToNext()) {

                contactID = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));

                contactNAME = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                contactphotoURI = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

                contactEmail = getEmail(cr, contactID);
                Contacts contact = null;

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    //Query phone here.
                    contactNumber = getPhone(cr, contactID);
                    if (Utils.checkNotEmpty(contactNumber)) {
                        contact = new Contacts();
                        contact.setNumber(contactNumber);
                        contact.setName(contactNAME);
                        contact.setId(contactID);
                        if (Utils.checkNotEmpty(contactphotoURI)) {

                            contact.setPhoto(contactphotoURI);
                        }
                        if (Utils.checkNotEmpty(contactEmail)) {
                            contact.setEmail(contactEmail);
                        }
                        contactsList.add(contact);
                    }


                }

            }

            cur.close();
        }

        Log.d(TAG, " Size" + contactsList.size());
        return contactsList;
    }

    public String getPhone(ContentResolver cr, String contactID) {

        String contactNumber = null;
        Cursor cursorPhone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone != null && cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        }

        if (cursorPhone != null)
            cursorPhone.close();

        return contactNumber;
    }

    public String getEmail(ContentResolver cr, String contactID) {
        String email = null;
        Cursor emailCur = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contactID}, null);
        while (emailCur != null && emailCur.moveToNext()) {
            // This would allow you get several email addresses
            // if the email addresses were stored in an array
            email = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));


        }
        if (emailCur != null)
            emailCur.close();

        return email;
    }


    public Observable<Contacts> getCallLog(Contacts contact) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            String SELECTION =
                    CallLog.Calls.CACHED_NAME + " LIKE ?";
            String[] mSelectionArgs = {contact.getName()};

            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, SELECTION, mSelectionArgs, null);

            if (cursor != null && cursor.getCount() > 0) {

                String duration, callDate, dateString = null;


                int numb = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
                int totalduration = 0;

                while (cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String phNum = cursor.getString(numb);

                    int callType = Integer.parseInt(cursor.getString(type));
                    duration = cursor.getString(cursor
                            .getColumnIndex(android.provider.CallLog.Calls.DURATION));
                    callDate = cursor.getString(cursor
                            .getColumnIndex(android.provider.CallLog.Calls.DATE));
                    SimpleDateFormat formatter = new SimpleDateFormat(
                            "dd-MMM-yyyy HH:mm", Locale.getDefault());

                    dateString = formatter.format(new Date(Long
                            .parseLong(callDate)));

                    //Log.d(TAG, contact.getName() +" "  + duration);
                    int dur = Integer.parseInt(duration);
                    if (dur > 0) {
                        totalduration = totalduration + dur;

                    }
                }

                contact.setDuration(totalduration);
                contact.setCallDate(dateString);


            }
            if (cursor != null)
                cursor.close();


        }
        return Observable.just(contact);

    }


    private Observable<List<Contacts>> getObservable() {
        return Observable.create(new ObservableOnSubscribe<List<Contacts>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Contacts>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(readAllContacts());
                    e.onComplete();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }
}
