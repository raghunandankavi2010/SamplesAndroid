package example.raghunandan.databinding.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import example.raghunandan.databinding.BR;


/**
 * Created by Raghunandan on 24-09-2016.
 */

public class User extends BaseObservable {
    private  String firstName;
    private  String lastName;


    public User() {

    }
    @Bindable
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }
}
