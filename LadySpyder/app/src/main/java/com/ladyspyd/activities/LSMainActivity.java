package com.ladyspyd.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.ladyspyd.R;
import com.ladyspyd.fragments.DetailFragment;
import com.ladyspyd.fragments.HomeFragment;
import com.ladyspyd.helpers.LSApp;

import io.fabric.sdk.android.Fabric;

public class LSMainActivity extends LSBaseActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnHeadlineSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Fabric.with(this, new Crashlytics());
        getMixPanel(LSMainActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            HomeFragment firstFragment = new HomeFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tvHeaderName = (TextView) headerView.findViewById(R.id.tv_user_name);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.tv_user_email);

        tvHeaderName.setText(LSApp.getInstance().getPrefs().getName());
        tvEmail.setText(LSApp.getInstance().getPrefs().getEmail());

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {


        if (getResources().getBoolean(R.bool.isTablet) && getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            try {
                int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
                Log.i("Fragment Count", "" + index);
                FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //super.onBackPressed();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

     /*   if (id == R.id.nav_home) {
            // Handle the camera action
        } else */
        if (id == R.id.nav_feed_backs) {
            Intent intent = new Intent(LSMainActivity.this, LSFeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "Hi Friends , \n" +
                    "I came across LadySpyder App in Play Store , Looks attractive with wide range of collections at reasonable rate with free worldwide shipping . For More Info https://ladyspyder.com");
            startActivity(Intent.createChooser(intent2, "Share via"));
        } else if (id == R.id.nav_logout) {
            LSApp.getInstance().getPrefs().setEmail("");
            LSApp.getInstance().getPrefs().setUserId("");
            LSApp.getInstance().getPrefs().setName("");
            LSApp.getInstance().getPrefs().setEmail("");
            LSApp.getInstance().getPrefs().setMobileNumber("");
            Intent intent = new Intent(LSMainActivity.this, LSLoginActivity.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(LSMainActivity.this, LSAboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {
            Intent intent = new Intent(LSMainActivity.this, LSContactUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {
            Intent intent = new Intent(LSMainActivity.this, LSFeedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(LSMainActivity.this, LSFaqActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_refund_policy) {
            Intent intent = new Intent(LSMainActivity.this, LSRefundPolicyActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_tracking) {
            Intent intent = new Intent(LSMainActivity.this, LSTrackingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_update_password) {
            Intent intent = new Intent(LSMainActivity.this, LSUpdatePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shipping) {
            Intent intent = new Intent(LSMainActivity.this, LSShippingActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void onCategoriesSelected(String title) {

        Log.i("Title", title);
        if (getResources().getBoolean(R.bool.isTablet) && findViewById(R.id.detail_container) != null) {
            DetailFragment newFragment = DetailFragment.getInstance(title);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.detail_container, newFragment, "frag_detail");

            //transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
     /*   if (articleFrag != null) {
            articleFrag.updateFragmentContents(title);
        } */
        } else {

            Intent intent = new Intent(LSMainActivity.this,MainActivity.class);
            intent.putExtra("title", title);
            startActivity(intent);
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
     /*       DetailFragment newFragment = DetailFragment.getInstance(title);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.add(R.id.fragment_container, newFragment, "frag_detail");
            // transaction.addToBackStack(null);
            transaction.addToBackStack("frag_detail");

            // Commit the transaction
            transaction.commit();*/
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
