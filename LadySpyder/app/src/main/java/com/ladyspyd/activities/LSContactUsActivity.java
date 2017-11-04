package com.ladyspyd.activities;

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

import com.ladyspyd.R;
import com.ladyspyd.helpers.LSApp;
import com.ladyspyd.helpers.LSCheckNetwork;
import com.ladyspyd.models.LSFeedBackResponseModel;
import com.ladyspyd.models.RequestAddContactsUs;
import com.ladyspyd.rest.APIInterface;
import com.ladyspyd.rest.ApiClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSContactUsActivity extends LSBaseActivity {
    EditText etName;
    EditText etMessage;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lscontact_us);
        updateFragmentTitle("Contact Us ", true, false);
        etName = (EditText) findViewById(R.id.et_emil);
        etMessage = (EditText) findViewById(R.id.et_feed_back);
        btnSignUp = (Button) findViewById(R.id.btn_login);

        etName.setText(LSApp.getInstance().getPrefs().getEmail());

        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    dismissKeyboard(etMessage);

                    if (LSCheckNetwork.isInternetAvailable(LSContactUsActivity.this)) {

                        if (TextUtils.isEmpty(etName.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(etMessage.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                        } else {
                            sendContactUs();
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

                if (LSCheckNetwork.isInternetAvailable(LSContactUsActivity.this)) {
                    if (TextUtils.isEmpty(etName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Enter Title", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etMessage.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Enter Desc", Toast.LENGTH_SHORT).show();
                    } else {
                        sendContactUs();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void sendContactUs() {
        hideSoftKeyboard(LSContactUsActivity.this);
        showProgressDialog(LSContactUsActivity.this);

        String userName = etName.getText().toString();
        String userPassword = etMessage.getText().toString();


        RequestAddContactsUs registerRequest = new RequestAddContactsUs(LSApp.getInstance().getPrefs().getEmail(), userPassword
        );

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);
        System.out.println("Final" + new Gson().toJson(registerRequest));

        Call<LSFeedBackResponseModel> call = appInterface.addcontactus(header, registerRequest);

        call.enqueue(new Callback<LSFeedBackResponseModel>() {
            @Override
            public void onResponse(Call<LSFeedBackResponseModel> call, Response<LSFeedBackResponseModel> response) {
                hideProgressDialog();
                Toast.makeText(LSContactUsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                etMessage.setText("");
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
