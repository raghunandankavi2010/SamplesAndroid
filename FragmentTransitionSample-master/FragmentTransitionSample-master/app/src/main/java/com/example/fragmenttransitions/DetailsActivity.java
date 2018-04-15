package com.example.fragmenttransitions;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        int kittenNumber = getIntent().getIntExtra("pos",0);
        DetailsFragment kittenDetails = DetailsFragment.newInstance(kittenNumber);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            kittenDetails.setSharedElementEnterTransition(new DetailsTransition());
            kittenDetails.setEnterTransition(new Fade());
            kittenDetails.setSharedElementReturnTransition(new DetailsTransition());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_details, kittenDetails)
                .commit();
    }

}
