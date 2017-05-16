package com.example.raghu.contactsdashboard_raghunandan;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by raghu on 16/5/17.
 */

public class CallLogModel {


    private static CallLogModel ourInstance = new CallLogModel();


    public static CallLogModel getInstance() {
        return ourInstance;
    }

    private CallLogModel() {
    }

    private static final String TAG = "Model";
    private List<Contacts> contactsList = new ArrayList<>();


    private Observable<Contacts> readContact(Contacts contact) {

        contactsList.clear();
        String contactNAME = null, contactID, contactphotoURI, contactNumber = null, contactEmail,dob=null;
        String[] PROJECTION = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,ContactsContract.Contacts.HAS_PHONE_NUMBER};
        String SELECTION = ContactsContract.Contacts.DISPLAY_NAME+ " LIKE ?" ;
        String[] mSelectionArgs = {contact.getName()};
        ContentResolver cr = MyApplication.getInstance().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                PROJECTION, SELECTION, mSelectionArgs, null);

        int indexid = cur.getColumnIndex(ContactsContract.Contacts._ID);
        int indexname = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int photouri = cur.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
        int hasphoneindex = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

        if (cur != null && cur.getCount() > 0) {

            while (cur.moveToNext()) {

                contactID = cur.getString(indexid);

                contactNAME = cur.getString(indexname);

                contactphotoURI = cur.getString(photouri);

                contactEmail = getEmail(cr, contactID);

                if (Integer.parseInt(cur.getString(hasphoneindex)) > 0) {
                    //Query phone here.
                    contactNumber = getPhone(cr, contactID);

                    if (Utils.checkNotEmpty(contactNumber)) {


                        if (PhoneNumberUtils.compare(contact.getNumber(), contactNumber)) {
                            dob = getDOB(cr, contactID);

                            Log.d(TAG, "Number :" + contact.getNumber()+" "+contactNumber);
                            //contact.setNumber(contactNumber);
                            contact.setName(contactNAME);
                            contact.setId(contactID);
                            if (Utils.checkNotEmpty(contactphotoURI)) {

                                contact.setPhoto(contactphotoURI);
                            }
                            if (Utils.checkNotEmpty(contactEmail)) {
                                contact.setEmail(contactEmail);
                            }
                            if (Utils.checkNotEmpty(dob)) {
                                contact.setDob(dob);
                                contact.setDuration(2 * contact.getDuration());
                            }
                        }
                    }

                }
            }


            cur.close();
        }

        Log.d(TAG, "Size" + contactsList.size());
        return Observable.just(contact);
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

    private String getDOB(ContentResolver cr,String contactId)
    {
        String birthday = null;

        String[] projection = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.START_DATE
        };

        String where = ContactsContract.CommonDataKinds.Event.TYPE + " = "  + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND " +
                ContactsContract.CommonDataKinds.Event.MIMETYPE + " = '" +
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " +
                ContactsContract.Data.CONTACT_ID + " = " + contactId;


        Cursor birthdayCur = cr.query(ContactsContract.Data.CONTENT_URI, projection, where, null, null);
        if (birthdayCur.getCount() > 0) {
            while (birthdayCur.moveToNext()) {
                birthday = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
            }
        }
        birthdayCur.close();


    return birthday;
    }


    public Observable<HashMap<String,Contacts>> getCallLog() {


        HashMap<String, Contacts> map = new HashMap<>();
        if (ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {


            String[] PROJECTION = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DURATION, CallLog.Calls.DATE};
            String SELECTION = CallLog.Calls.DURATION + " > " + String.valueOf(0);
            //String[] mSelectionArgs = {String.valueOf(0)};

            Cursor cursor = MyApplication.getInstance().getContentResolver().query(CallLog.Calls.CONTENT_URI, PROJECTION, SELECTION, null, null);
            Contacts contacts ;
            if (cursor != null && cursor.getCount() > 0) {

                String duration, callDate, dateString = null;


                while (cursor.moveToNext()) {

                    int numb = cursor.getColumnIndex(CallLog.Calls.NUMBER);


                    // int type = cursor.getColumnIndex(CallLog.Calls.TYPE);

                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String phNum = cursor.getString(numb);
                    phNum = PhoneNumberUtils.formatNumber(phNum);
                    int totalduration = 0;
                    if (Utils.checkNotEmpty(name)) {
                        if (!map.containsKey(phNum)) {

                            contacts = new Contacts();
                            contacts.setName(name);
                            contacts.setNumber(phNum);

                            map.put(phNum, contacts);
                            String contactid = getContactIDFromNumber(phNum, MyApplication.getInstance().getApplicationContext().getContentResolver());
                            contacts.setId(contactid);


                            // int callType = Integer.parseInt(cursor.getString(type));
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

                            totalduration = totalduration + dur;
                            contacts.setDuration(totalduration);
                            contacts.setCallDate(dateString);

                        } else {
                            contacts = map.get(phNum);
                            duration = cursor.getString(cursor
                                    .getColumnIndex(android.provider.CallLog.Calls.DURATION));
                            callDate = cursor.getString(cursor
                                    .getColumnIndex(android.provider.CallLog.Calls.DATE));
                            SimpleDateFormat formatter = new SimpleDateFormat(
                                    "dd-MMM-yyyy HH:mm", Locale.getDefault());

                            dateString = formatter.format(new Date(Long
                                    .parseLong(callDate)));

                            //Log.d(TAG, contacts.getName() +" "  + duration);
                            int dur = Integer.parseInt(duration);

                            totalduration = totalduration + dur;
                            if (contacts != null) {
                                contacts.setCallDate(dateString);
                                contacts.setDuration(totalduration);

                            }


                        }
                    }


                }
            }
            if (cursor != null)
                cursor.close();

           /*for (Contacts contact : map.values()) {

                Log.d(TAG, "" + contact.getName());
                Log.d(TAG, "" + contact.getNumber());
                Log.d(TAG, "" + contact.getDuration());
                Log.d(TAG, "" + contact.getCallDate());
            }*/

        }
        return Observable.just(map);

    }

    public String getContactIDFromNumber(String contactNumber, ContentResolver contentResolver) {
        contactNumber = Uri.encode(contactNumber);
        int phoneContactID = new Random().nextInt();
        Cursor contactLookupCursor = contentResolver.query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contactNumber), new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        while (contactLookupCursor.moveToNext()) {
            phoneContactID = contactLookupCursor.getInt(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
        }
        contactLookupCursor.close();

        return String.valueOf(phoneContactID);
    }






    public Single<List<Contacts>> getDetails(boolean resetCache) {
        Single<List<Contacts>> listSingle;
        if (resetCache) {
            cacher.reset();
            listSingle = Single.unsafeCreate(cacher);
        } else {
            listSingle = Single.unsafeCreate(cacher);
        }
        return listSingle;

    }

    private Single<List<Contacts>> cachedObservable =
            getCallLog()
            .map(new Function<HashMap<String,Contacts>, List<Contacts>>() {
                @Override
                public List<Contacts> apply(@NonNull HashMap<String, Contacts> stringContactsHashMap) throws Exception {
                    return new ArrayList<>(stringContactsHashMap.values());
                }
            })
            .flatMapIterable(new Function<List<Contacts>, Iterable<Contacts>>() {
                @Override
                public Iterable<Contacts> apply(@NonNull List<Contacts> integers) throws Exception {
                    return integers;
                }
            })
            .flatMap(new Function<Contacts, ObservableSource<Contacts>>() {
                @Override
                public ObservableSource<Contacts> apply(Contacts contacts) throws Exception {
                    return readContact(contacts);
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
            });

    private OnSubScribeRefreshingCache<List<Contacts>> cacher = new OnSubScribeRefreshingCache<>(cachedObservable);



}
