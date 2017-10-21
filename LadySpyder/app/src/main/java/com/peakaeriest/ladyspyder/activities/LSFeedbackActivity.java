package com.peakaeriest.ladyspyder.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.helpers.LSApp;
import com.peakaeriest.ladyspyder.helpers.LSCheckNetwork;
import com.peakaeriest.ladyspyder.models.FeedBackRequest;
import com.peakaeriest.ladyspyder.models.LSFeedBackResponseModel;
import com.peakaeriest.ladyspyder.rest.APIInterface;
import com.peakaeriest.ladyspyder.rest.ApiClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSFeedbackActivity extends LSBaseActivity {
    EditText etName;
    EditText etPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsfeedback);
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.input_password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_in);
        updateFragmentTitle("Feedback", true, false);

        etName.setText(LSApp.getInstance().getPrefs().getEmail());

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    dismissKeyboard(etPassword);

                    if (LSCheckNetwork.isInternetAvailable(LSFeedbackActivity.this)) {

                        if (TextUtils.isEmpty(etName.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                        } else {
                            sendFeedback();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LSCheckNetwork.isInternetAvailable(LSFeedbackActivity.this)) {
                    if (TextUtils.isEmpty(etName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Enter Title", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Enter Desc", Toast.LENGTH_SHORT).show();
                    } else {
                        sendFeedback();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void sendFeedback() {
        hideSoftKeyboard(LSFeedbackActivity.this);
        showProgressDialog(LSFeedbackActivity.this);

        String userName = etName.getText().toString();
        String userPassword = etPassword.getText().toString();


        FeedBackRequest registerRequest = new FeedBackRequest(LSApp.getInstance().getPrefs().getEmail(), userName, userPassword
        );

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);
        System.out.println("Final" + new Gson().toJson(registerRequest));

        Call<LSFeedBackResponseModel> call = appInterface.registerUser(header, registerRequest);

        call.enqueue(new Callback<LSFeedBackResponseModel>() {
            @Override
            public void onResponse(Call<LSFeedBackResponseModel> call, Response<LSFeedBackResponseModel> response) {
                hideProgressDialog();
                Toast.makeText(LSFeedbackActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                hideSoftKeyboard(LSFeedbackActivity.this);
                etName.setText("");
                etPassword.setText("");
            }

            @Override
            public void onFailure(Call<LSFeedBackResponseModel> call, Throwable t) {

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
