package assignment.com.raghu.androdiassignment.listeners;

/**
 * Created by raghu on 29/7/17.
 */

public interface OnLoginValidateListener {

    void onLoginPhoneError(String message);

    void onLoginPasswordError(String message);

    void register_newUser(String message);

    void onLoginSuccess();

    void onLoginFailed();



}