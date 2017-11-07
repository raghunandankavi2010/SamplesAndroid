package com.ladyspyd.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ladyspyd.R;
import com.ladyspyd.helpers.LSApp;
import com.ladyspyd.helpers.LSCheckNetwork;
import com.ladyspyd.helpers.LSUtilities;
import com.ladyspyd.models.RegistrationRequest;
import com.ladyspyd.models.RegistrationResponseModel;
import com.ladyspyd.rest.APIInterface;
import com.ladyspyd.rest.ApiClient;
import com.ladyspyd.utils.Utilities;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSRegisterActivity extends AppCompatActivity {

    private EditText etName,etPhoneNumber,etEmailID,etPassword;
    private Button btnSignUp;
    private ProgressDialog mProgressDialog;
    private Call<RegistrationResponseModel> call;
    private CoordinatorLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsregister);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        root = findViewById(R.id.root);
        etName = (EditText) findViewById(R.id.et_name);
        etPhoneNumber = (EditText) findViewById(R.id.et_mobile_number);
        etEmailID = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_in);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null)
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (LSCheckNetwork.isInternetAvailable(LSRegisterActivity.this)) {

                    if (TextUtils.isEmpty(etName.getText().toString())) {
                        etName.setError("Name cannot be empty");

                    } else if (TextUtils.isEmpty(etPhoneNumber.getText().toString()) ) {
                        etPhoneNumber.setError("Phone Number cannot be empty");
                    } else if (!Utilities.isvalidemail(etEmailID.getText().toString())) {
                        etEmailID.setError("Enter valid E-mail Id");
                    } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                        etPassword.setError("Password cannot be empty");
                    }

                    else {
                        registerUser();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(root, "No internet connection.Try again later!.", Snackbar.LENGTH_LONG);

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
    protected void onDestroy() {
        super.onDestroy();
        if(call!=null){
            call.cancel();
        }
        hideProgressDialog();
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

    public void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = LSUtilities.createProgressDialog(context);
            mProgressDialog.show();
        } else {
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    public MixpanelAPI getMixPanel(Context context) {
        MixpanelAPI mixpanel = null;
        try {
            String projectToken = "5aa99d5f40f7c5121e005174e4b3b8c1"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
            mixpanel = MixpanelAPI.getInstance(context, projectToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mixpanel;

    }
}
