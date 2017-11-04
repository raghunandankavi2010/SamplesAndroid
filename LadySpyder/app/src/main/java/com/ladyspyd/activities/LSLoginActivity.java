package com.ladyspyd.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;

import com.ladyspyd.R;
import com.ladyspyd.helpers.LSApp;
import com.ladyspyd.helpers.LSCheckNetwork;
import com.ladyspyd.models.LoginRequestModel;
import com.ladyspyd.models.LoginResponseModel;
import com.ladyspyd.rest.APIInterface;
import com.ladyspyd.rest.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LSLoginActivity extends LSBaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    @BindView(R.id.tv_signup)
    TextView tvSignUp;
    @BindView(R.id.link_signup)
    TextView tvForgotPassword;
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnLogin;
    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;
    CheckBox chkBtn;
    TextView tvAgree;

    @BindView(R.id.sv)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lslogin);
        ButterKnife.bind(this);
        tvAgree = (TextView) findViewById(R.id.tvAgree);
        chkBtn = (CheckBox) findViewById(R.id.saveLoginCheckBox);

        if (TextUtils.isEmpty(LSApp.getInstance().getPrefs().getEmail())) {

        } else {
            startActivity(new Intent(LSLoginActivity.this, LSMainActivity.class));
            finish();
        }
        tvAgree.setClickable(true);
        tvAgree.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "I agree to LadySpyder Privacy Policy <a href=\"https://ladyspyder.com/terms-and-conditions/\"> ";
        tvAgree.setText(Html.fromHtml(text));

        tvAgree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                chkBtn.setChecked(true);
                String url = "https://ladyspyder.com/terms-and-conditions/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftKeyboard(LSLoginActivity.this);

                    if (LSCheckNetwork.isInternetAvailable(LSLoginActivity.this)) {
                        if (!isvalidemail(etEmail.getText().toString())) {
                            Toast.makeText(LSLoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                            Toast.makeText(LSLoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();

                        } else if (!chkBtn.isChecked()) {
                            showAlert(LSLoginActivity.this, "Info", "Please Agree to terms of Usage to continue the registration process");
                        } else {
                            checkUser();
                        }
                    } else {
                        Toast.makeText(LSLoginActivity.this, "No internet Found please enable it", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });


        tvForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LSLoginActivity.this, LSForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        tvSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LSLoginActivity.this, LSRegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public void signup() {
        Intent intent = new Intent(LSLoginActivity.this, LSRegisterActivity.class);
        startActivity(intent);
    }

    public void forgotpassword() {
        Intent intent = new Intent(LSLoginActivity.this, LSRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void checkUser() {
        showProgressDialog(LSLoginActivity.this);
        String userName = etEmail.getText().toString();
        String userPassword = etPassword.getText().toString();

        LoginRequestModel loginRequest = new LoginRequestModel(userName, userPassword);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);
        System.out.println("Final" + new Gson().toJson(loginRequest));

        Call<LoginResponseModel> call = appInterface.loginUser(header, loginRequest);

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        LSApp.getInstance().getPrefs().setBadgeCount(0);
                        LSApp.getInstance().getPrefs().setFavCount(0);
                        try {
                            JSONObject props = new JSONObject();
                            props.put("Logged in", true);
                            getMixPanel(LSLoginActivity.this).track("LSLogin Activity - Login  called", props);
                        } catch (JSONException e) {
                            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                        }


                        saveInPreference(response.body().getUser());
                        startActivity(new Intent(LSLoginActivity.this, LSMainActivity.class));
                        LSLoginActivity.this.finish();


                    } else {
                        Toast.makeText(LSLoginActivity.this, "Username or mobile already exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LSLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
                Toast.makeText(LSLoginActivity.this, "Something went wrong in server side", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveInPreference(LoginResponseModel.User user) {
//        Save in Preference
        LSApp.getInstance().getPrefs().setUserId(String.valueOf(user.getId()));
        LSApp.getInstance().getPrefs().setName(user.getName());
        LSApp.getInstance().getPrefs().setEmail(user.getEmail());
        LSApp.getInstance().getPrefs().setMobileNumber(user.getMobile());
        LSApp.getInstance().getPrefs().setStatus(user.getStatus());
        LSApp.getInstance().getPrefs().createLoginSession();

    }

    @OnClick(R.id.btn_sign_in)
    public void submit() {
        hideSoftKeyboard(LSLoginActivity.this);
        if (LSCheckNetwork.isInternetAvailable(LSLoginActivity.this)) {
            if (TextUtils.isEmpty(etEmail.getText().toString()) || !isvalidemail(etEmail.getText().toString())) {
                etEmail.setError("Enter a valid E-mail");
            } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                etPassword.setError("Password cannot be empty");
            } else if (!chkBtn.isChecked()) {
                showAlert(LSLoginActivity.this, "Info", "Please Agree to terms of Usage to continue the registration process");
            } else {
                checkUser();
            }
        } else {
            Snackbar snackbar = Snackbar
                    .make(scrollView, "No internet connection.Try again later!.", Snackbar.LENGTH_LONG);

            snackbar.show();
        }


    }
}

