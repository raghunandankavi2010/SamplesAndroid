package com.indiainnovates.pucho;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.indiainnovates.pucho.fragments.SettingsFragment;

/**
 * Created by yash on 20/2/16.
 */
public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(getResources().getString(R.string.action_settings));

        toolbar.setNavigationIcon(R.drawable.ic_up);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction().replace(R.id.settings_framelayout, new SettingsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
