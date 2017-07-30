package assignment.com.raghu.androdiassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import assignment.com.raghu.androdiassignment.dagger.modules.components.ApplicationComponent;
import assignment.com.raghu.androdiassignment.model.User;
import io.realm.Realm;

import static assignment.com.raghu.androdiassignment.R.id.ph;

/**
 * Created by raghu on 29/7/17.
 */

public class ProfileActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent appComponent = ((DemoApplication) getApplication()).getApplicationComponent();
        appComponent.inject(this);
        boolean logged_in = sharedPreferences.getBoolean("loggedin",false);
        if(logged_in) {
            setContentView(R.layout.profile_screen);

            Button logout = (Button) findViewById(R.id.button);
            TextView name = (TextView) findViewById(R.id.name);
            TextView phnumber = (TextView) findViewById(ph);
            TextView email = (TextView) findViewById(R.id.email);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin",false);
                    editor.apply();
                    Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).findFirst();
            if(user!=null) {
                name.setText(user.getName());
                phnumber.setText(user.getPhone());
                email.setText(user.getEmail());
            }

            realm.close();



        }else {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
