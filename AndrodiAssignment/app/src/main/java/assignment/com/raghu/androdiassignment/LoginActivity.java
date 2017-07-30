package assignment.com.raghu.androdiassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import assignment.com.raghu.androdiassignment.dagger.modules.components.ActivityComponent;
import assignment.com.raghu.androdiassignment.presenters.LoginPresenterContract;
import timber.log.Timber;

/**
 * Created by raghu on 28/7/17.
 */

public class LoginActivity extends BaseActivity<LoginPresenterContract.Presenter>  implements LoginPresenterContract.View {


    private Button login,register;
    private RelativeLayout rl;
    private EditText phone,password;
    private ProgressBar pb;


    @Inject
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        //sharedPreferences= getSharedPreferences("prefs", Context.MODE_PRIVATE);

        pb = (ProgressBar) this.findViewById(R.id.pb);
        rl = (RelativeLayout) this.findViewById(R.id.rl);
        login = (AppCompatButton) this.findViewById(R.id.btn_login);
        phone = (EditText) this.findViewById(R.id.input_phone);
        password = (EditText) this.findViewById(R.id.input_password);
        register = (AppCompatButton)  this.findViewById(R.id.create_account);
        TextView forgot_password = (TextView) findViewById(R.id.link_signup);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               presenter.validate(phone.getText().toString().trim(),password.getText().toString().trim());


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void injectFrom(ActivityComponent activityComponent) {

        activityComponent.inject(this);
    }



    @Override
    public void showProgress(boolean active) {

        if(active) {
            pb.setVisibility(View.VISIBLE);
        }else {
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSnackBar(String message) {

        Snackbar.make(rl,message,Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void passwordError() {

        Timber.i("Invalid password");
        password.setError("Must contain atleast 1 number,1 lowercase , 1 upper case and 1 special character");
    }

    @Override
    public void phoneError() {

        Timber.i("Invalid phone number");
        phone.setError("Number must be like eg. 91xxxxxxxxxx");
    }

    @Override
    public void loginSuccess() {

        Timber.i("Login Successful");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loggedin",true);
        editor.apply();
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {

        Timber.i("Login Failed");
    }
}
