package innovates.com.pucho;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import innovates.com.pucho.adapters.QuestionAdapter;
import innovates.com.pucho.models.Answers;
import innovates.com.pucho.models.Questions;
import innovates.com.pucho.models.User;
import innovates.com.pucho.utils.UrlStrings;
import innovates.com.pucho.utils.Utility;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.AsyncTask;


public class QuestionFeed extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private android.support.design.widget.FloatingActionButton fab;
    private SharedPreferences sharedPreferences;
    private String id;
    private Questions qs;
    private Answers ans;
    private TextView tv;
    private QuestionAdapter mAdapter;
    private ProgressBar pb;
    private int pageCount=1;
    private ArrayList<Questions> mquestionsArrayList;
    private Handler handler;
    private int totalCount;
    private static final int per_page = 3;
    private AccountHeaderBuilder accountHeader;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private IProfile profile;
    private Drawable drawable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        handler = new Handler();

        sharedPreferences = getSharedPreferences("User_ID", MODE_PRIVATE);
        id = sharedPreferences.getString("user_id", null);
        if(!TextUtils.isEmpty(id)) {
            setContentView(R.layout.activity_question_feed);
            /*pb = (ProgressBar) this.findViewById(R.id.progressBar);
            tv = (TextView) this.findViewById(R.id.list_empty);
            fab = (android.support.design.widget.FloatingActionButton)this.findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(QuestionFeed.this,PostQuestion.class);
                    startActivity(intent);
                }
            });

            *//* Use toolbar as actionbar *//*
            toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
            toolbar.setTitle("Questions");


            this.setSupportActionBar(toolbar);

            toolbar.setNavigationIcon(R.drawable.ic_drawer);

            recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getResources().getDrawable(R.mipmap.ic_launcher, getTheme());
            } else {
                drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            }
            String name = sharedPreferences.getString("user_name",null);
            String email = sharedPreferences.getString("user_email",null);
            String url = sharedPreferences.getString("user_profile_url",null);
            if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(url)) {
                Uri myUri = Uri.parse(url);
                profile = new ProfileDrawerItem().withName(name)
                        .withEmail(email)
                        .withIcon(myUri);
            }


        //initialize and create the image loader logic
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                    Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
                }

                @Override
                public void cancel(ImageView imageView) {
                    Picasso.with(imageView.getContext()).cancelRequest(imageView);
                }

            });


            buildHeader(false, savedInstanceState);

            //Create the drawer
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Drawer Item1").withIcon(drawable).withIdentifier(1).withSelectable(true),
                            new PrimaryDrawerItem().withName("Drawer Item2").withIcon(drawable).withIdentifier(2).withSelectable(true),
                            new PrimaryDrawerItem().withName("Drawer Item3").withIcon(drawable).withIdentifier(3).withSelectable(true),
                            new PrimaryDrawerItem().withName("Drawer Item4").withIcon(drawable).withIdentifier(4).withSelectable(true)

                    ) // add the items we want to use with our Drawer
                    .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                        @Override
                        public boolean onNavigationClickListener(View clickedView) {
                            //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                            //if the back arrow is shown. close the activity
                            QuestionFeed.this.finish();
                            //return true if we have consumed the event
                            return false;
                        }
                    }).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {

                            if (drawerItem != null) {
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 1) {
                                    Toast.makeText(getApplicationContext(), "Drawer item 1 Clicked", Toast.LENGTH_SHORT).show();
                                    //intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleCompactHeaderDrawerActivity.class);
                                } else if (drawerItem.getIdentifier() == 2) {
                                    Toast.makeText(getApplicationContext(), "Drawer item 2 Clicked", Toast.LENGTH_SHORT).show();
                                    //intent = new Intent(SimpleHeaderDrawerActivity.this, ActionBarDrawerActivity.class);
                                } else if (drawerItem.getIdentifier() == 3) {
                                    Toast.makeText(getApplicationContext(), "Drawer item 3 Clicked", Toast.LENGTH_SHORT).show();
                                    // intent = new Intent(SimpleHeaderDrawerActivity.this, MultiDrawerActivity.class);
                                } else if (drawerItem.getIdentifier() == 4) {
                                    Toast.makeText(getApplicationContext(), "Drawer item 4 Clicked", Toast.LENGTH_SHORT).show();
                                    //intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleNonTranslucentDrawerActivity.class);

                                }
                                if (intent != null) {
                                    QuestionFeed.this.startActivity(intent);
                                }

                            }
                            return false;
                        }
                    })

                    .addStickyDrawerItems(
                            new SecondaryDrawerItem().withName("Settings").withIcon(drawable).withIdentifier(10),
                            new SecondaryDrawerItem().withName("Open Source").withIcon(drawable)
                    )
                    .withSavedInstance(savedInstanceState)
                    .build();

            //only set the active selection or active profile if we do not recreate the activity
            if (savedInstanceState == null) {
                // set the selection to the item with the identifier 11
                result.setSelection(1, false);

                //set the active profile
                headerResult.setActiveProfile(profile);
            }

            mLinearLayoutManager = new LinearLayoutManager(QuestionFeed.this);
            recyclerView.setLayoutManager(mLinearLayoutManager);
            DefaultItemAnimator animator = new DefaultItemAnimator();
            animator.setAddDuration(5000);
            recyclerView.setItemAnimator(animator);
            recyclerView.setHasFixedSize(true);
            mAdapter = new QuestionAdapter();
            mquestionsArrayList = new ArrayList<>(0);
            mAdapter.setInitData(mquestionsArrayList);
            recyclerView.setAdapter(mAdapter);



            recyclerView.addOnScrollListener(new EndlessScrollListener(mLinearLayoutManager) {
                @Override
                public void onLoadMore(int current_page, int totalItemCount) {
                    //add progress item
                    Log.i("Count is",""+current_page);
                    pageCount = current_page;
                    //mquestionsArrayList.add(null);
                    //mAdapter.notifyItemInserted(mquestionsArrayList.size());
                    if(totalCount!=totalItemCount) {
                        new FetchQuestion().execute(current_page);
                    }else {

                    }

                }
            });



           if(CheckNetwork.isInternetAvailable(QuestionFeed.this))
            {
                new FetchQuestion().execute(pageCount);
            }else
            {

                tv.setVisibility(View.VISIBLE);
                tv.setText("Check your Network Connection!.Try again Later!");
            }*/
        }
        else
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ask_question, menu);
        return true;
    }


    private class FetchQuestion extends AsyncTask<Integer,Void, ArrayList<Questions>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fab.setVisibility(View.GONE);

            if(pageCount>1)
            {
                mquestionsArrayList.add(null);
                mAdapter.notifyItemInserted(mquestionsArrayList.size());
            }else
            {
                recyclerView.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected  ArrayList<Questions> doInBackground(Integer... value) {
            Log.i("Count is",""+value[0].intValue());
            ArrayList<Questions> questionsArrayList=fetchQuestions(value[0].intValue());
            return questionsArrayList;
        }

        @Override
        protected void onPostExecute(final ArrayList<Questions> result) {
            super.onPostExecute(result);
            Log.i("...................",""+ result);

            if(result.size()>0 && pageCount == 1)
            {
                pb.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                mquestionsArrayList = result;
                mAdapter.setInitData(mquestionsArrayList);

                mAdapter.notifyDataSetChanged();


            }else if(pageCount>1)
            {
                Log.i("...................",""+ (mquestionsArrayList.size()));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mquestionsArrayList.remove(mquestionsArrayList.size() - 1);
                        if(result!=null && result.size()>0)
                        mquestionsArrayList.addAll(result);
                        fab.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 5000); // 5 Second dealy in update just for testing purpose


            }
            else {
                pb.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                tv.setText("Something Wrong!.Try again Later!");
            }

        }
    }


    public ArrayList<Questions> fetchQuestions(int count){
        ArrayList<Questions> questions;
        ArrayList<Answers> answers;
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        String response_json="";
        int statusCode = 0;
        User user;
        questions = new ArrayList<Questions>();
        try {

             //URL url = new URL(UrlStrings.BASE_URL+"/questions?page="+count+"&per_page="+per_page);
             URL url = new URL(UrlStrings.BASE_URL+"/multiLanQuestion/pa");

            //establish the connection
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            //make some HTTP header nicety
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            //open
            conn.connect();

            statusCode = conn.getResponseCode();
            if (statusCode >= conn.HTTP_BAD_REQUEST)
              is = conn.getErrorStream();
            else
            {
            is = conn.getInputStream();
            response_json = Utility.getStringFromInputStream(is);
                Log.i("Wall Post", response_json);

            //Extract the Whole JSON Array

            JSONObject jb = new JSONObject(response_json);
            if(pageCount>1) {

             }else {
                // only first time parse. Skip from second time.
                totalCount = jb.getInt("total");
                mquestionsArrayList.ensureCapacity(totalCount);

            }
            JSONArray data = jb.getJSONArray("data");
            for (int j = 0; j < data.length(); j++) {
                JSONObject qs_Obj = data.getJSONObject(j);
                qs = new Questions();
                qs.setActive(qs_Obj.getBoolean("active"));
                qs.setUserID(qs_Obj.getInt("userId"));
                qs.setQuestionID(qs_Obj.getInt("id"));
                qs.setContent(qs_Obj.getString("content"));
                qs.setAskedOn(qs_Obj.getString("askedOn"));

                user = new User();
                String usr = qs_Obj.getString("user");
                JSONObject json_User = new JSONObject(usr);
                user.setActive(json_User.getBoolean("active"));
                user.setUserId(String.valueOf(json_User.getInt("id")));
                user.setUserName(json_User.getString("username"));
                user.setEmail("email");
                user.setProfession("profession");
                qs.setUser(user);
                //Extract the Answer Array
                JSONArray ans_arr = new JSONArray(qs_Obj.getString("answers"));
                answers = new ArrayList<Answers>();

                for (int i = 0; i < ans_arr.length(); i++) {
                    ans = new Answers();
                    JSONObject row = ans_arr.getJSONObject(i);
                    ans.setAnswerID(row.getInt("id"));
                    ans.setActive(row.getBoolean("active"));
                    ans.setQuestionID(row.getInt("questionId"));
                    ans.setUserID(row.getInt("userId"));
                    ans.setContent(row.getString("content"));
                    ans.setUpvote(row.getInt("upvote"));
                    ans.setDownvote(row.getInt("downvote"));
                    ans.setAnsweredOn(row.getString("answerdOn"));
                    user = new User();
                    String usr_1 = row.getString("user");
                    JSONObject json_User_1 = new JSONObject(usr_1);
                    user.setActive(json_User_1.getBoolean("active"));
                    user.setUserId(String.valueOf(json_User_1.getInt("id")));
                    user.setUserName(json_User_1.getString("username"));
                    ans.setUserName(json_User_1.getString("username"));
                    user.setEmail(json_User_1.getString("email"));

                    user.setProfession(json_User_1.getString("profession"));
                    ans.setProfession(json_User_1.getString("profession"));
                    ans.setUser(user);
                    //ans.setAnsweredOn(row.getString("answerdOn"));
                    answers.add(ans);
                }
                qs.setAnswers(answers);
                //mquestionsArrayList.add(qs);
                questions.add(qs);
            }
            }

        }catch(Exception e){
            e.printStackTrace();
        } finally {
            //clean up
            try {
                if(os!=null && is!=null && conn!=null) {
                    os.close();
                    is.close();
                    conn.disconnect();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return  questions;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.search:
                MenuItem searchMenuItem = item;
                searchMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

                SearchView searchView = (SearchView) searchMenuItem.getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.onActionViewExpanded();
                searchView.setQueryHint("Question Name");


                MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mAdapter.setData();
                        mAdapter.notifyDataSetChanged();
                        return true;
                    }
                });


                        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // this is your adapter that will be filtered
                                mAdapter.getFilter().filter(newText);
                                mAdapter.notifyDataSetChanged();
                                return true;
                            }

                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                // this is your adapter that will be filtered
                                mAdapter.getFilter().filter(query);
                                mAdapter.notifyDataSetChanged();
                                return true;
                            }
                        };
                searchView.setOnQueryTextListener(textChangeListener);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param compact
     * @param savedInstanceState
     */
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader

        accountHeader = new AccountHeaderBuilder();

        headerResult = accountHeader
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profile
                )
        /*        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })*/
                .withSavedInstance(savedInstanceState)
                .build();

        Log.i("CCC", "" + sharedPreferences.getString("cover_photo", null));

        String url = sharedPreferences.getString("cover_photo",null);

        if(!TextUtils.isEmpty(url))
        {
             ImageView imageView = headerResult.getHeaderBackgroundView();
             Picasso.with(QuestionFeed.this).load(url).placeholder(R.drawable.header).into(imageView);
        }



    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}

