package com.indiainnovates.pucho;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.indiainnovates.pucho.animations.ZoomOutPageTransformer;
import com.indiainnovates.pucho.fragments.FavoriteFragment;
import com.indiainnovates.pucho.fragments.FeedFragment;
import com.indiainnovates.pucho.fragments.TrendingFragment;
import com.indiainnovates.pucho.listeners.OnFragmentCallback;
import com.indiainnovates.pucho.presenters.QuestionFeedPresenter;
import com.indiainnovates.pucho.utils.CircleCropTransformation;
import com.indiainnovates.pucho.widgets.BezelImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;



public class QuestionFeed extends AppCompatActivity implements OnFragmentCallback {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Glide glide;

    private DrawerLayout mDrawerLayout;
    private int id;
    private String content;
    private ProgressBar pb;


    // Gcm var declaration
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    // end of gcm var declaration

    private static final String TAG = "QuestionFeed";
    private FloatingActionButton fab;

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        /* Necessary to prevent drawer from being opened when navigating to different activity */
        final View view = mDrawerLayout.getChildAt(mDrawerLayout.getChildCount() - 1);
        ViewTreeObserver vto = view.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(view.getWidth(), view.getHeight());
                lp.gravity = Gravity.LEFT;
                view.setLayoutParams(lp);
                view.setLeft(-view.getMeasuredWidth());
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });

    }

    @Inject
    QuestionFeedPresenter questionFeedPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);

        PuchoApplication.component().inject(this);

        id = sharedPreferences.getInt("user_id", -1);
        if (id != -1) {


            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sharedPreferences
                            .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                    if (sentToken) {
                        Log.i(TAG, " Token Sent to Server");
                        // token sent to server
                    } else {
                        Log.i(TAG, " Token not Sent to Server");
                        // token not sent
                    }
                }
            };

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
            setContentView(R.layout.activity_home);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
            ab.setDisplayHomeAsUpEnabled(true);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            if (navigationView != null) {
                setupDrawerContent(navigationView);
            }


            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            viewPager.setPageMargin(16);
            if (viewPager != null) {
                setupViewPager(viewPager);
            }

            fab = (FloatingActionButton) findViewById(R.id.fab);

            scaleFab(fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(QuestionFeed.this, PostQuestion.class);
                    startActivity(intent);
                }});


            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ask_question, menu);
        return true;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new FeedFragment(), getResources().getString(R.string.feed));
        adapter.addFragment(new TrendingFragment(), getResources().getString(R.string.trending));
        adapter.addFragment(new FavoriteFragment(), getResources().getString(R.string.favorites));
        adapter.addFragment(new TrendingFragment(), getResources().getString(R.string.notifications));
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private void setupDrawerContent(NavigationView navigationView) {

        View headerLayout = navigationView.getHeaderView(0);
        TextView name = (TextView) headerLayout.findViewById(R.id.name);
        TextView email = (TextView) headerLayout.findViewById(R.id.email);
        BezelImageView profileIcon = (BezelImageView) headerLayout.findViewById(R.id.circleView);
        String url = sharedPreferences.getString("photo_uri", "");
        if (!TextUtils.isEmpty(url)) {

            glide.with(this)
                    .load(Uri.parse(url))
                    .placeholder(R.drawable.person_image_empty)
                    .error(R.drawable.person_image_empty)
                    .transform(new CircleCropTransformation(QuestionFeed.this))
                    .into(profileIcon);
        }else {
            Drawable res = ContextCompat.getDrawable(this,R.drawable.person_image_empty);
            profileIcon.setImageDrawable(res);
        }
        name.setText(sharedPreferences.getString("user_name", " "));
        email.setText(sharedPreferences.getString("user_email", " "));
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        Intent intent;
                        switch (menuItem.getItemId()) {

                            case R.id.nav_questions:
                                 intent = new Intent(QuestionFeed.this,MyQuestions.class);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(), "Questions", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_answers:
                                Toast.makeText(getApplicationContext(), "Answers", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_settings:
                                 intent = new Intent(QuestionFeed.this,SettingsActivity.class);
                                startActivity(intent);
                                break;

                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentCallback(String content) {

        this.content = content;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_intent_title)));
        }
    }

    public void scaleFab(FloatingActionButton fab) {
        Animation scale_Up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.scale_up);
        fab.startAnimation(scale_Up);

    }

}


