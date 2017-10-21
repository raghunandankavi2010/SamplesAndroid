package com.peakaeriest.ladyspyder.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
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
import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.helpers.LSApp;
import com.peakaeriest.ladyspyder.helpers.LSCheckNetwork;
import com.peakaeriest.ladyspyder.models.UpdatePasswordRequestModel;
import com.peakaeriest.ladyspyder.models.UpdatePasswordResponseModel;
import com.peakaeriest.ladyspyder.rest.APIInterface;
import com.peakaeriest.ladyspyder.rest.ApiClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSUpdatePasswordActivity extends LSBaseActivity {
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnLogin;
    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsupdate_password);
        updateFragmentTitle("Update Password", true, false);
        ButterKnife.bind(LSUpdatePasswordActivity.this);

        etEmail.setText(LSApp.getInstance().getPrefs().getEmail());
        etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftKeyboard(LSUpdatePasswordActivity.this);

                    if (LSCheckNetwork.isInternetAvailable(LSUpdatePasswordActivity.this)) {
                        if (!isvalidemail(etEmail.getText().toString())) {
                            Toast.makeText(LSUpdatePasswordActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                            Toast.makeText(LSUpdatePasswordActivity.this, "Enter Password", Toast.LENGTH_LONG).show();

                        } else if (TextUtils.isEmpty(et_confirm_password.getText().toString())) {
                            Toast.makeText(LSUpdatePasswordActivity.this, "Enter Confirm Password", Toast.LENGTH_LONG).show();

                        } else if (!etPassword.getText().toString().equalsIgnoreCase(et_confirm_password.getText().toString())) {
                            Toast.makeText(LSUpdatePasswordActivity.this, "Enter valid password", Toast.LENGTH_LONG).show();

                        } else {
                            checkUser();
                        }
                    } else {
                        Toast.makeText(LSUpdatePasswordActivity.this, "No internet Found please enable it", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(LSUpdatePasswordActivity.this);
                if (LSCheckNetwork.isInternetAvailable(LSUpdatePasswordActivity.this)) {
                    if (!isvalidemail(etEmail.getText().toString())) {
                        Toast.makeText(LSUpdatePasswordActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                        Toast.makeText(LSUpdatePasswordActivity.this, "Enter Password", Toast.LENGTH_LONG).show();

                    } else if (TextUtils.isEmpty(et_confirm_password.getText().toString())) {
                        Toast.makeText(LSUpdatePasswordActivity.this, "Enter Confirm Password", Toast.LENGTH_LONG).show();

                    } else if (!etPassword.getText().toString().equalsIgnoreCase(et_confirm_password.getText().toString())) {
                        Toast.makeText(LSUpdatePasswordActivity.this, "Enter valid password", Toast.LENGTH_LONG).show();

                    } else {
                        checkUser();
                    }
                } else {
                    Toast.makeText(LSUpdatePasswordActivity.this, "No internet Found please enable it", Toast.LENGTH_LONG).show();
                }
            }

        });
    }


    private void checkUser() {
        showProgressDialog(LSUpdatePasswordActivity.this);
        String userName = etEmail.getText().toString();
        String userPassword = etPassword.getText().toString();

        UpdatePasswordRequestModel loginRequest = new UpdatePasswordRequestModel(userName, userPassword, userPassword);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);
        System.out.println("Final" + new Gson().toJson(loginRequest));

        Call<UpdatePasswordResponseModel> call = appInterface.updatePassword(header, loginRequest);

        call.enqueue(new Callback<UpdatePasswordResponseModel>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponseModel> call, Response<UpdatePasswordResponseModel> response) {
                hideProgressDialog();
                Toast.makeText(LSUpdatePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
                et_confirm_password.setText("");
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponseModel> call, Throwable t) {
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
