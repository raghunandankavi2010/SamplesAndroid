package assignment.com.raghu.androdiassignment.listeners;

/**
 * Created by raghu on 29/7/17.
 */

public interface OnRegisterListener {


    void onPhoneError(String message);

    void onPasswordError(String messge);


    void onNamedError(String messge);
    void onEmailError(String messge);

    void onRegisteruccess();

    void onUserAlreadyExists();

}
