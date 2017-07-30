package assignment.com.raghu.androdiassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import assignment.com.raghu.androdiassignment.dagger.modules.components.ActivityComponent;
import assignment.com.raghu.androdiassignment.presenters.RegisterPresenterContract;
import timber.log.Timber;

/**
 * Created by raghu on 29/7/17.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenterContract.Presenter> implements RegisterPresenterContract.View {


    private EditText email, ph, password, name;
    private ProgressBar pb;
    private RelativeLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        ph = (EditText) this.findViewById(R.id.ph);
        password = (EditText) this.findViewById(R.id.password);
        pb = (ProgressBar) this.findViewById(R.id.pb);
        rl = (RelativeLayout) this.findViewById(R.id.rl);

        name = (EditText) this.findViewById(R.id.name);
        email = (EditText) this.findViewById(R.id.email);

        Button register = (AppCompatButton) this.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.register(ph.getText().toString().trim(), email.getText().toString().trim(), name.getText().toString().trim(), password.getText().toString());
            }
        });

    }

    @Override
    protected void injectFrom(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }



    @Override
    public void showProgress(boolean active) {
        if(active){
            pb.setVisibility(View.VISIBLE);
        } else {
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
        ph.setError("Number must be like eg. 91xxxxxxxxxx");
    }

    @Override
    public void emailError() {

        Timber.i("EMail not valid");
        email.setError("Enter valid email");
    }

    @Override
    public void namerror() {

        Timber.i("Name entered is not valid");
        name.setError("Enter a valid name with no spaces between characters");
    }

    @Override
    public void registerSuccess() {

        Timber.i("Registration Success");

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void registerFailed() {

        Timber.i("Registration Failed");
    }
}
