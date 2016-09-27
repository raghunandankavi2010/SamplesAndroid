package example.raghunandan.databinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.raghunandan.databinding.databinding.ActivityLaunchBinding;

/**
 * Created by Raghunandan on 27-09-2016.
 */

public class LaunchActivity extends AppCompatActivity {

    private ActivityLaunchBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_launch);
        binding.setHandlers(this);

    }

    public void onClickSimple(View view) {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void onClickRecycler(View view) {

        Intent intent = new Intent(this,RecyclerActivity.class);
        startActivity(intent);
    }

    public void onClickMVVM(View view) {

        Intent intent = new Intent(this,FeedActivity.class);
        startActivity(intent);
    }
}
