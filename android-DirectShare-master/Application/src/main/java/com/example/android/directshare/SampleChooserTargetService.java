/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.directshare;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.chooser.ChooserTarget;
import android.service.chooser.ChooserTargetService;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides the Direct Share items to the system.
 */
public class SampleChooserTargetService extends ChooserTargetService {


    Uri uri = ContactsContract.Contacts.CONTENT_URI;

    ContentResolver contentResolver ;

    private String number ;

    @Override
    public void onCreate() {
        super.onCreate();
        contentResolver = getContentResolver();
    }
    int i=0;

    @Override
    public List<ChooserTarget> onGetChooserTargets(ComponentName targetActivityName,
                                                   IntentFilter matchedFilter) {
        ComponentName componentName = new ComponentName(getPackageName(),
                SendMessageActivity.class.getCanonicalName());
        // The list of Direct Share items. The system will show the items the way they are sorted
        // in this list.
        ArrayList<ChooserTarget> targets = new ArrayList<>();

        Cursor cur = contentResolver.query(uri,
                null, null, null, null);
        if (cur.getCount() > 0) {
            Contact.setArrayCount(cur.getCount());
            while (cur.moveToNext()) {

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));



                Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {

                    String number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int type = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    switch (type) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            // do something with the Home number here...
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:

                           this.number = number;

                            // do something with the Mobile number here...
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            // do something with the Work number here...
                            break;
                    }
                }
                pCur.close();


                Contact.CONTACTS[i] = new Contact(name);
                Contact contact = Contact.byId(i);
                Bundle extras = new Bundle();
                extras.putInt(Contact.ID, i);
                Icon icon;
                if(image_uri!=null)
                {
                    icon = Icon.createWithContentUri(image_uri);
                }
                else
                {
                    icon = Icon.createWithResource(this, contact.getIcon());
                }
                if(!TextUtils.isEmpty(number))

                    contact.addMobileNumber(number);

                i++;
                targets.add(new ChooserTarget(
                        // The name of this target.
                        name,
                        // The icon to represent this target.
                        icon,
                        // The ranking score for this target (0.0-1.0); the system will omit items with
                        // low scores when there are too many Direct Share items.
                        0.5f,
                        // The name of the component to be launched if this target is chosen.
                        componentName,
                        // The extra values here will be merged into the Intent when this target is
                        // chosen.
                        extras));


            }

        }
        return targets;
    }
}


/*
for (int i = 0; i < Contact.CONTACTS.length; ++i) {
        Contact contact = Contact.byId(i);
        Bundle extras = new Bundle();
        extras.putInt(Contact.ID, i);
        targets.add(new ChooserTarget(
        // The name of this target.
        contact.getName(),
        // The icon to represent this target.
        Icon.createWithResource(this, contact.getIcon()),
        // The ranking score for this target (0.0-1.0); the system will omit items with
        // low scores when there are too many Direct Share items.
        0.5f,
        // The name of the component to be launched if this target is chosen.
        componentName,
        // The extra values here will be merged into the Intent when this target is
        // chosen.
        extras));
        }*/
