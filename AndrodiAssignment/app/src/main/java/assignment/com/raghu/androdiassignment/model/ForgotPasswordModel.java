package assignment.com.raghu.androdiassignment.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import assignment.com.raghu.androdiassignment.listeners.OnForgotPasswordListener;
import assignment.com.raghu.androdiassignment.utils.Utility;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by raghu on 30/7/17.
 */

public class ForgotPasswordModel {

    public void resetPassword(@Nullable String phnumber, OnForgotPasswordListener onForgotPasswordListener) {


        if(!TextUtils.isEmpty(phnumber) && Utility.validatePhoneNumber(phnumber)) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<User> mUsers = realm.where(User.class).findAll();

            User mUser = null;
            if (mUsers.size() > 0) {
                for (User users : realm.where(User.class).findAll()) {

                    if (users.getPhone().equals(phnumber)) {
                        mUser = users;
                    }

                }
            } else {
                // no user exist
                onForgotPasswordListener.onPhoneNumberNotExist("Entered Phone Number does not exist");
            }

            if (mUser != null) {

                String password = mUser.getPassword();
                onForgotPasswordListener.onPassword(password);
            }else {
                onForgotPasswordListener.onPhoneNumberNotExist("Entered Phone Number does not exist");
            }
            realm.close();
        } else {
            onForgotPasswordListener.onPhoneError("Enter a valid phone number");
        }

    }
}
