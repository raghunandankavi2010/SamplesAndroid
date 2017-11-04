package com.ladyspyd.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.ladyspyd.R;
import com.ladyspyd.helpers.LSCheckNetwork;
import com.ladyspyd.models.ForogotRequestModel;
import com.ladyspyd.models.LSForgotpasswordResponseModel;
import com.ladyspyd.rest.APIInterface;
import com.ladyspyd.rest.ApiClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSForgotPasswordActivity extends LSBaseActivity {
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnLogin;
    @BindView(R.id.et_mobile_number)
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsforgot_password);

        updateFragmentTitle("Forgot Password", true, false);
        ButterKnife.bind(LSForgotPasswordActivity.this);

        etEmail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftKeyboard(LSForgotPasswordActivity.this);

                    if (LSCheckNetwork.isInternetAvailable(LSForgotPasswordActivity.this)) {
                        if (!isvalidemail(etEmail.getText().toString())) {
                            Toast.makeText(LSForgotPasswordActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                        } else {
                            checkUser();
                        }
                    } else {
                        Toast.makeText(LSForgotPasswordActivity.this, "No internet Found please enable it", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(LSForgotPasswordActivity.this);
                if (LSCheckNetwork.isInternetAvailable(LSForgotPasswordActivity.this)) {
                    if (!isvalidemail(etEmail.getText().toString())) {
                        Toast.makeText(LSForgotPasswordActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                    } else {
                        checkUser();
                    }
                } else {
                    Toast.makeText(LSForgotPasswordActivity.this, "No internet Found please enable it", Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    private void checkUser() {
        showProgressDialog(LSForgotPasswordActivity.this);
        String userName = etEmail.getText().toString();

        ForogotRequestModel loginRequest = new ForogotRequestModel(userName);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);
        System.out.println("Final" + new Gson().toJson(loginRequest));

        Call<LSForgotpasswordResponseModel> call = appInterface.forgotpassword(header, loginRequest);
        call.enqueue(new Callback<LSForgotpasswordResponseModel>() {
            @Override
            public void onResponse(Call<LSForgotpasswordResponseModel> call, Response<LSForgotpasswordResponseModel> response) {
                hideProgressDialog();

                try {
                    if (response.body().getError()) {
                        Toast.makeText(LSForgotPasswordActivity.this, "Sorry, this email does not exist", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LSForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LSForgotpasswordResponseModel> call, Throwable t) {
                t.printStackTrace();
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
