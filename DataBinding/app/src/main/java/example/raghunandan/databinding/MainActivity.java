package example.raghunandan.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import example.raghunandan.databinding.databinding.ActivityMainBinding;
import example.raghunandan.databinding.models.User;


public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        user = new User();
        user.setFirstName("Raghu");
        user.setLastName("Kavi");
        binding.setUser(user);
        binding.setHandlers(this);

    }

    public void onButtonClick(View view) {

        Toast.makeText(view.getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
        user.setFirstName("Raghunandan");
    }


}
