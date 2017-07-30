package assignment.com.raghu.androdiassignment.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by raghu on 29/7/17.
 */

public class User extends RealmObject {


    @Required
    private String phone;
    @Required
    private String email;
    @PrimaryKey
    @Required
    private String name;
    @Required
    private String password;
    // getters and setters


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;

    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
