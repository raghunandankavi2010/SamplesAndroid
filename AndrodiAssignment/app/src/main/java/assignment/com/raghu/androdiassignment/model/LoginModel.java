package assignment.com.raghu.androdiassignment.model;

import android.text.TextUtils;

import assignment.com.raghu.androdiassignment.listeners.OnLoginValidateListener;
import assignment.com.raghu.androdiassignment.utils.Utility;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by raghu on 29/7/17.
 */

public class LoginModel {

    private boolean check_password,check_phonenumber;

    public void validateCredentials(final String ph,final  String password,final OnLoginValidateListener onLoginValidateListener) {
      if(!TextUtils.isEmpty(ph) && Utility.validatePhoneNumber(ph)){
          check_phonenumber = true;

        }else {
          check_phonenumber =false;
          onLoginValidateListener.onLoginPhoneError("Phone number must be a valid 10 digit number");
      }

        if (!TextUtils.isEmpty(password) && Utility.validatePasswordRegex(password)) {

            check_password = true;
        } else {
           onLoginValidateListener.onLoginPasswordError("Entered password is not valid");
            check_password =false;
        }

        if(check_password && check_phonenumber)  {

            Realm realm = Realm.getDefaultInstance();

            RealmResults<User> mUsers = realm.where(User.class).findAll();
            if(mUsers.size()>0) {


                for (User users : mUsers) {

                    if (users.getPhone() == null) {
                        onLoginValidateListener.register_newUser("User does not exist. Register!");
                    } else if (users.getPhone().equals(ph) && users.getPassword().equals(password)) {

                        Timber.i(users.getPhone());
                        Timber.i(users.getPassword());
                        onLoginValidateListener.onLoginSuccess();
                    }

                }
            }else {
                onLoginValidateListener.register_newUser("User does not exist. Register!");
            }
            realm.close();


        }
    }
}
