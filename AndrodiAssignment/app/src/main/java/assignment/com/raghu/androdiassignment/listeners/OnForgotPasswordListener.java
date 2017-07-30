package assignment.com.raghu.androdiassignment.listeners;

/**
 * Created by raghu on 30/7/17.
 */

public interface OnForgotPasswordListener {

    void onPhoneError(String message);

    void onPhoneNumberNotExist(String message);

    void onPassword(String password);
}
