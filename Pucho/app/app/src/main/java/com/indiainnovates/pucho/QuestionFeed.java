package com.indiainnovates.pucho;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

//import com.indiainnovates.pucho.dagger.components.DaggerApplicationComponent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.indiainnovates.pucho.fragments.FavoriteFragment;
import com.indiainnovates.pucho.fragments.FeedFragment;
import com.indiainnovates.pucho.fragments.TrendingFragment;
import com.indiainnovates.pucho.listeners.OnFragmentCallback;
import com.indiainnovates.pucho.presenters.QuestionFeedPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

//import com.indiainnovates.pucho.adapters.QuestionAdapter;


public class QuestionFeed extends AppCompatActivity implements OnFragmentCallback {

    /*private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;*/
    @Inject
    SharedPreferences sharedPreferences;
    /*
    private Questions qs;
    private Answers ans;
    private TextView tv;
    private QuestionAdapter mAdapter;
    private ProgressBar pb;
    private int pageCount = 1;
    private ArrayList<Questions> mquestionsArrayList;
    private Handler handler;
    private int totalCount;
    private static final int per_page = 10;*/

    private DrawerLayout mDrawerLayout;
    private int id;
    private String content;


    final private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;

    // Gcm var declaration
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    // end of gcm var declaration

    private static final String TAG = "QuestionFeed";
    private FloatingActionButton fab;

    private static final int HEADER_HIDE_ANIM_DURATION = 300;

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

    @Inject
    QuestionFeedPresenter questionFeedPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);

        ((PuchoApplication) getApplication()).component().inject(this);

        if(savedInstanceState!=null)
        {
            Log.i("savedInstanceState","Not Null");
        }else {
            Log.i("savedInstanceState"," Null");
        }

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
                }
            });

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
        adapter.addFragment(new FeedFragment(), "FEED");
        adapter.addFragment(new TrendingFragment(), "TRENDING");
        adapter.addFragment(new FavoriteFragment(), "FAVORITES");
        adapter.addFragment(new TrendingFragment(), "NOTIFICATIONS");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        //fab.setVisibility(View.VISIBLE);
                        //scaleFab(fab);
                        break;
                    default:
                        //scaleDownFab(fab);
                        //fab.setVisibility(View.GONE);
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
        name.setText(sharedPreferences.getString("user_name", " "));
        email.setText(sharedPreferences.getString("user_email", " "));
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                Toast.makeText(getApplicationContext(), "Home Drawer item", Toast.LENGTH_SHORT).show();
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
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentCallback(String content) {

        this.content = content;
        // checkPermissionForReadContacts();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_intent_title)));
        }
    }

    @Override
    public void onHideFab(boolean shown) {

        if (shown) {

            fab.animate()
                    .translationY(fab.getBottom()+dipsToPix(16))
                    .setInterpolator(new AccelerateInterpolator(2))
                    .start();

        } else {

            fab.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();

        }
    }

    public void checkPermissionForReadContacts() {

        Log.i("Checking permission", "Yeah");
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(QuestionFeed.this,
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(QuestionFeed.this,
                    android.Manifest.permission.READ_CONTACTS)) {

                Log.i("ShowRationale", "Yeah");
                showMessageOKCancel("You need to allow access to Contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(QuestionFeed.this, new String[]{android.Manifest.permission.READ_CONTACTS},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        });

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                Log.i("No Explanation Needed", "Yeah");
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(QuestionFeed.this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, content);
            sendIntent.setType("text/plain");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(sendIntent, getString(R.string.send_intent_title)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                    sendIntent.setType("text/plain");
                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(Intent.createChooser(sendIntent, getString(R.string.send_intent_title)));
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(QuestionFeed.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void scaleFab(FloatingActionButton fab) {
        Animation scale_Up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.scale_up);
        fab.startAnimation(scale_Up);

    }

    private void scaleDownFab(FloatingActionButton fab) {

        Animation scale_Down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.scale_down);
        fab.startAnimation(scale_Down);
    }

       private int dipsToPix(float dps) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dps, getResources().getDisplayMetrics());
    }
}


