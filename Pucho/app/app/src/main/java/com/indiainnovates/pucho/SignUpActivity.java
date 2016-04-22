package com.indiainnovates.pucho;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.indiainnovates.pucho.events.ErrorEvent;
import com.indiainnovates.pucho.events.SuccessSignUpResponseEvent;
import com.indiainnovates.pucho.presenters.SignUpPresenter;
import com.indiainnovates.pucho.screen_contracts.SignUpScreen;

import javax.inject.Inject;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Raghunandan on 23-12-2015.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpScreen {

    private EditText name, email, password;
    private Button singup;
    private ProgressDialog pd;
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ((PuchoApplication) getApplication()).component().inject(this);
        EventBus.getDefault().register(this);

         signUpPresenter.setSignUpScreen(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Sign Up");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setTitle("Signing up");

        singup = (Button) this.findViewById(R.id.signUp2);
        singup.setOnClickListener(this);

        name = (EditText) this.findViewById(R.id.fullName);
        email = (EditText) this.findViewById(R.id.emailAddress);
        password = (EditText) this.findViewById(R.id.passwordSignUp);
        //mobile_number = (EditText) this.findViewById(R.id.passwordSignUp);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signUp2:
                String number;
                signUpPresenter.setName(name.getText().toString());
                signUpPresenter.setEmail(email.getText().toString());
                signUpPresenter.setPassword(password.getText().toString());

                signUpPresenter.valiDate_SignUp();
                break;
        }
    }
   /* Invalid email,password or user Name */
    @Subscribe
    public void onEvent(String resp) {
        /* Do something */
        if(resp.equals("User Name Error"))
        {
            onNameError();
        }
        else if(resp.equals("Password Error"))
        {
            onPassWordError();
        }
        else if(resp.equals("Email Error"))
        {
          onEmailError();
        }
        else if(resp.equals("Success"))
        {
          onValidateSuccess();
        }
    }
    /* Post details to server success*/
    @Subscribe
    public void onEvent(SuccessSignUpResponseEvent event) {
        /* Do something */

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id",event.getListSignUpResponse().get(0).getId() );
        editor.putString("user_name",event.getListSignUpResponse().get(0).getFullName());
        editor.putString("user_email",event.getListSignUpResponse().get(0).getEmail());

        editor.apply();
        Intent intent = new Intent(this,QuestionFeed.class);
        startActivity(intent);
        finish();
        Toast.makeText(this.getApplicationContext(),""+event.getListSignUpResponse().get(0).getId(),Toast.LENGTH_SHORT).show();
    }

    /* Something went wrong. Error */
    @Subscribe
    public void onEvent(ErrorEvent resp) {
        /* Do something */
        if(pd!=null && pd.isShowing())
        {
            pd.dismiss();
            resp.getErrorEvent().printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void onPassWordError() {
        password.setError("Invalid Password");
    }


    public void onNameError() {

        name.setError("Invalid Name");
    }


    public void onEmailError() {
        email.setError("Invalid Email");
    }

    public void onValidateSuccess() {

        Log.i("Validation Succes","Yeah!");
        signUpPresenter.setName(name.getText().toString());
        signUpPresenter.setEmail(email.getText().toString());
        signUpPresenter.setPassword(password.getText().toString());
        signUpPresenter.sendDetailsToserver();
        signUpPresenter.showProgressDialog();
    }

    @Override
    public void onShowProgressDialog() {

        pd.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        signUpPresenter.onDestroyActivity();
    }

    /*   @Override
    public void onNext(List<SuccessSignUpResponse> response) {
        pd.dismiss();
       //if(response.g)
        Toast.makeText(this.getApplicationContext(),""+response.get(0).getId(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompleted() {
        pd.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        pd.dismiss();
        e.printStackTrace();
    }*/
}
