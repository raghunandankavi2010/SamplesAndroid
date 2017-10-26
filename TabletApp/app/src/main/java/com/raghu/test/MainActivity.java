package com.raghu.test;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.raghu.test.settings.PrefUtils;

public class MainActivity extends AppCompatActivity {

    static Boolean mTwoPane;

    Toolbar mToolbar;
    NavigationView mNavigationView;
    NavigationView mNavigationView_partial;
    DrawerLayout mDrawerLayout;

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        mUserLearnedDrawer = Boolean.valueOf(PrefUtils.readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        if (findViewById(R.id.drawer) != null) {
//            Phone layout
            mTwoPane = false;

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            setUpNavDrawer();

        } else {
//            Tablet layout
            mTwoPane = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
            }

        }
        if(getResources().getBoolean(R.bool.isTablet)){
            final SlidingPaneLayout layout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
            layout.setSliderFadeColor(Color.TRANSPARENT);
            mNavigationView_partial = (NavigationView) findViewById(R.id.nav_view_partial);
            mNavigationView_partial.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    Toast.makeText(MainActivity.this.getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                    menuItem.setChecked(true);
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_item_home:
//                        Do something

                            mCurrentSelectedPosition = 0;
                            return true;
                        case R.id.navigation_item_explore:

//                        Do something
                            mCurrentSelectedPosition = 1;
                            return true;
                        case R.id.navigation_item_following:

//                        Do something
                            mCurrentSelectedPosition = 2;
                            return true;
                        case R.id.navigation_item_favourites:

//                        Do something
                            mCurrentSelectedPosition = 3;
                            return true;
                        case R.id.navigation_item_settings:

//                        Do something
                            mCurrentSelectedPosition = 4;
                            return true;
                        case R.id.navigation_item_help:
//                        Do something
                            mCurrentSelectedPosition = 5;
                            return true;
                        default:
                            return true;
                    }
                }
            });
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Toast.makeText(MainActivity.this.getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_home:
//                        Do something

                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.navigation_item_explore:

//                        Do something
                        mCurrentSelectedPosition = 1;
                        return true;
                    case R.id.navigation_item_following:

//                        Do something
                        mCurrentSelectedPosition = 2;
                        return true;
                    case R.id.navigation_item_favourites:

//                        Do something
                        mCurrentSelectedPosition = 3;
                        return true;
                    case R.id.navigation_item_settings:

//                        Do something
                        mCurrentSelectedPosition = 4;
                        return true;
                    case R.id.navigation_item_help:
//                        Do something

                        mCurrentSelectedPosition = 5;
                        return true;
                    default:
                        return true;
                }
            }
        });

    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        if (!mUserLearnedDrawer) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mUserLearnedDrawer = true;
            PrefUtils.saveSharedSetting(this, PREF_USER_LEARNED_DRAWER, "true");
        }
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
        outState.putBoolean("TWO_PANE", mTwoPane);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTwoPane = savedInstanceState.getBoolean("TWO_PANE");
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
