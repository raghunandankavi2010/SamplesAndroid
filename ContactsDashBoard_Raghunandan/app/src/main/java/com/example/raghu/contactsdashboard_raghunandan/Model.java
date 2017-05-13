package com.example.raghu.contactsdashboard_raghunandan;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by raghu on 12/5/17.
 */

public class Model {


    private static Model ourInstance = new Model();
    

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }
    private static final String TAG = "Model";
    private List<Contacts> contactsList = new ArrayList<>();

    private List<Contacts> readAllContacts() {

        String contactNAME = null, contactID, contactphotoURI, contactNumber = null, contactEmail;
        ContentResolver cr = MyApplication.getInstance().getContentResolver();
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

                        contactNumber = PhoneNumberUtils.formatNumber(contactNumber);

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

        Log.d(TAG, "Size" + contactsList.size());
        return contactsList;
    }

    private String getPhone(ContentResolver cr, String contactID) {

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

    private String getEmail(ContentResolver cr, String contactID) {
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


    private Observable<Contacts> getCallLog(Contacts contact) {

        if (ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            String SELECTION = CallLog.Calls.CACHED_NAME + " LIKE ?"; /*+"AND " +*/
            //CallLog.Calls.NUMBER + " LIKE ?";
            String[] mSelectionArgs = {contact.getName()};

            Cursor cursor = MyApplication.getInstance().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, SELECTION, mSelectionArgs, null);

            if (cursor != null && cursor.getCount() > 0) {

                String duration, callDate, dateString = null;


                int numb = cursor.getColumnIndex(CallLog.Calls.NUMBER);

                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
                int totalduration = 0;

                while (cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String phNum = cursor.getString(numb);
                    Log.d(TAG, "CallLog Name : " + name);
                    Log.d(TAG, "CallLog Number : " + phNum);
                    // you get call logs if call log name matches with phone contact name
                    // However the number in the contact may have country code as 09
                    // while in call log it may be +91. So compare both.
                    // return true if they're identical enough for caller ID purposes
                    if(PhoneNumberUtils.compare(contact.getNumber(),phNum)) {

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

    public Single<List<Contacts>> getDetails()
    {
        return cachedObservable;

    }

    private Single<List<Contacts>> cachedObservable = getObservable()
            .flatMapIterable(new Function<List<Contacts>, Iterable<Contacts>>() {
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
            }).cache();

}
