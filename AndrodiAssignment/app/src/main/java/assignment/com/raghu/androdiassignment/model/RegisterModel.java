package assignment.com.raghu.androdiassignment.model;

import android.text.TextUtils;

import assignment.com.raghu.androdiassignment.listeners.OnRegisterListener;
import assignment.com.raghu.androdiassignment.utils.Utility;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by raghu on 29/7/17.
 */

public class RegisterModel {




    private boolean check_phonenumber,check_password,check_email,check_name;
    public void insertDataIntoRealm(final String phone, final String email,final  String name,final String password, final OnRegisterListener onRegisterListener) {

        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class, name);
                user.setPhone(phone);
                user.setEmail(email);
                //user.setName(name);
                user.setPassword(password);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //realm already update
                onRegisterListener.onRegisteruccess();
                realm.close();
            }
        });


        realm.close();
    }


    public void validateCredentials(final String ph,final String email,final String name,final  String password,final OnRegisterListener onRegisterListener) {
        if(!TextUtils.isEmpty(ph) && Utility.validatePhoneNumber(ph)){
            check_phonenumber = true;

        }else {
            check_phonenumber =false;
            onRegisterListener.onPhoneError("Phone number must be a valid 10 digit number");
        }

        if (!TextUtils.isEmpty(password) && Utility.validatePasswordRegex(password)) {

            check_password = true;
        } else {
            onRegisterListener.onPasswordError("Entered password is not valid");
            check_password =false;
        }


        if (!TextUtils.isEmpty(name) && Utility.validateNameRegex(name)) {

            check_name = true;
        } else {
            onRegisterListener.onNamedError("Enter a name");
            check_name =false;
        }


        if (!TextUtils.isEmpty(email) && Utility.validateEmailRegex(email)) {

            check_email = true;
        } else {
            onRegisterListener.onEmailError("Entered a valid email");
            check_email =false;
        }

        if(check_password && check_phonenumber && check_email && check_name)  {

            Realm realm = Realm.getDefaultInstance();
            RealmResults<User> mUsers = realm.where(User.class).findAll();
            if(mUsers.size()>0) {
                for (User users : realm.where(User.class).findAll()) {

                    if (users.getName() == null) {
                        insertDataIntoRealm(ph, email, name, password, onRegisterListener);
                    } else {

                        onRegisterListener.onUserAlreadyExists();
                    }

                }
            }else {
                insertDataIntoRealm(ph, email, name, password, onRegisterListener);
            }
            realm.close();

        }
    }
}
