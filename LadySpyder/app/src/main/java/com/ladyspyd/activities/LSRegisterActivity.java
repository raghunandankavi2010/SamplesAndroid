package com.ladyspyd.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.ladyspyd.R;
import com.ladyspyd.helpers.LSApp;
import com.ladyspyd.helpers.LSCheckNetwork;
import com.ladyspyd.models.RegistrationRequest;
import com.ladyspyd.models.RegistrationResponseModel;
import com.ladyspyd.rest.APIInterface;
import com.ladyspyd.rest.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSRegisterActivity extends LSBaseActivity {
    EditText etName;
    EditText etPhoneNumber;
    EditText etEmailID;
    EditText etPassword;
    Button btnSignUp;


    @BindView(R.id.sv)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsregister);
        etName = (EditText) findViewById(R.id.et_name);
        etPhoneNumber = (EditText) findViewById(R.id.et_mobile_number);
        etEmailID = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_in);


        updateFragmentTitle("Register", true, false);


/*        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    dismissKeyboard(etPassword);

                    if (LSCheckNetwork.isInternetAvailable(LSRegisterActivity.this)) {

                        if (TextUtils.isEmpty(etName.getText().toString())) {
                            etName.setError("Name cannot be empty");

                        } else if (TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
                            etPhoneNumber.setError("Phone Number cannot be empty");
                        } else if (TextUtils.isEmpty(etEmailID.getText().toString()) && !isvalidemail(etEmailID.getText().toString())) {
                            etEmailID.setError("Enter valid E-mail Id");
                        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                            etPassword.setError("Password cannot be empty");
                        }

                        else {
                            registerUser();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });*/


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LSCheckNetwork.isInternetAvailable(LSRegisterActivity.this)) {

                    if (TextUtils.isEmpty(etName.getText().toString())) {
                        etName.setError("Name cannot be empty");

                    } else if (TextUtils.isEmpty(etPhoneNumber.getText().toString()) ) {
                        etPhoneNumber.setError("Phone Number cannot be empty");
                    } else if (!isvalidemail(etEmailID.getText().toString())) {
                        etEmailID.setError("Enter valid E-mail Id");
                    } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                        etPassword.setError("Password cannot be empty");
                    }

                    else {
                        registerUser();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(scrollView, "No internet connection.Try again later!.", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

            }
        });
    }


    private void registerUser() {
        showProgressDialog(LSRegisterActivity.this);

        String userName = etName.getText().toString();
        String userPhoneNumber = etPhoneNumber.getText().toString();
        String userEmailID = etEmailID.getText().toString();
        String userPassword = etPassword.getText().toString();


        RegistrationRequest registerRequest = new RegistrationRequest(userName, userEmailID, userPhoneNumber, userPassword, LSApp.sDefSystemLanguageCode);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);
        System.out.println("Final" + new Gson().toJson(registerRequest));

        Call<RegistrationResponseModel> call = appInterface.registerUser(header, registerRequest);

        call.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, Response<RegistrationResponseModel> response) {
                hideProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject props = new JSONObject();
                            props.put("Registered User", true);
                            getMixPanel(LSRegisterActivity.this).track("LSUser Register Activity - Register Success called", props);
                        } catch (JSONException e) {
                            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                        }
                        Toast.makeText(LSRegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (!response.body().getError()) {
                            onBackPressed();
                        }
                    } else {
                        Toast.makeText(LSRegisterActivity.this, "Mobile or Email Already Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kd_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
