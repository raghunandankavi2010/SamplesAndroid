package com.example.raghu.constraintlayout;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    private ConstraintSet constraintSet01 = new ConstraintSet();
    private ConstraintSet constraintSet02 = new ConstraintSet();
    private Boolean original = true;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_linear_layout);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linear_layout);
        View view  =  getLayoutInflater().inflate(R.layout.content_constraintset_01,ll);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraint_layout);
        setUpAppBar();

        constraintSet01.clone(constraintLayout);
        constraintSet02.clone(this, R.layout.content_constraintset_02);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.constraint_set, menu);
        MenuItem menuItem = menu.findItem(R.id.action_swap_constraint_set);
        DrawableCompat.setTint(menuItem.getIcon(), Color.WHITE);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_swap_constraint_set :
                TransitionManager.beginDelayedTransition(constraintLayout);
                if (original) constraintSet02.applyTo(constraintLayout);
                else constraintSet01.applyTo(constraintLayout);
                original = !original;
                return true;
            default: return super.onOptionsItemSelected(item);

        }
    }

    public void setUpAppBar() {
        // Set up app bar.
        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
}
