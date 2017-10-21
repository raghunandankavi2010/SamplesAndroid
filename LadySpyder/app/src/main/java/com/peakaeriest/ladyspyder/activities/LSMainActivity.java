package com.peakaeriest.ladyspyder.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.adapters.KDCategoryListAdapter;
import com.peakaeriest.ladyspyder.helpers.LSApp;
import com.peakaeriest.ladyspyder.helpers.LSCheckNetwork;
import com.peakaeriest.ladyspyder.models.GetMainCategoryResponse;
import com.peakaeriest.ladyspyder.models.LSBannerModel;
import com.peakaeriest.ladyspyder.models.LSPopularProductPojo;
import com.peakaeriest.ladyspyder.rest.APIInterface;
import com.peakaeriest.ladyspyder.rest.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSMainActivity extends LSBaseActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private List<LSPopularProductPojo> mProductPojo;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int currentpage = 0;
    KDCategoryListAdapter listAdapter;
    ExpandableListView exListView;
    List<String> listDataHeader;
    HashMap<String, List<GetMainCategoryResponse.SubCategory>> listDataChild;
    private ProgressDialog mProgressDialog;
    private ProgressBar pb;
    Handler handler = new Handler();
    private CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pb = (ProgressBar)findViewById(R.id.pb);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProductPojo = new ArrayList<>();
        exListView = (ExpandableListView) findViewById(R.id.lvExp);
        exListView.setVisibility(View.GONE);

        if (LSCheckNetwork.isInternetAvailable(LSMainActivity.this)) {
            getMainCategories();
            getBanners();
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tvHeaderName = (TextView) headerView.findViewById(R.id.tv_user_name);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.tv_user_email);

        tvHeaderName.setText(LSApp.getInstance().getPrefs().getName());
        tvEmail.setText(LSApp.getInstance().getPrefs().getEmail());

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init(List<LSBannerModel.Banner> banners) {
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        slider_image_list = new ArrayList<>();
        for (int i = 0; i < banners.size(); i++) {
            slider_image_list.add(banners.get(i).getBannerImage());
        }

        Runnable runnableCode = new Runnable() {

            public void run() {
                if (currentpage == slider_image_list.size() - 1) {
                    currentpage = 0;
                }
                vp_slider.setCurrentItem(++currentpage, true);
                handler.postDelayed(this, 4000); // determines the execution interval
            }

        };


        handler.postDelayed(runnableCode, 4000);
        sliderPagerAdapter = new SliderPagerAdapter(LSMainActivity.this, slider_image_list);
        vp_slider.setAdapter(sliderPagerAdapter);
        vp_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentpage = position;
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // List view Group click listener
        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                /**
                 * check if there is a sub category.
                 * expand the same.
                 * if not sub category goto detail view
                 * with main category
                 * */
              if( listDataHeader.get(groupPosition)!=null &&  listDataChild.get(listDataHeader.get(groupPosition))==null) {
                    String title = listDataHeader.get(groupPosition);
                    title = title.replace(' ', '-');

                    //Toast.makeText(LSMainActivity.this, listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LSMainActivity.this, MainActivity.class);
                    intent.putExtra("title", title);

                    startActivity(intent);
                }
                return false;
            }
        });

        // List view Group expanded listener
        exListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {


            }
        });

        // List view Group collasped listener
        exListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
               /* Intent intent = new Intent(getActivity(), ProctDetail.class);
                intent.putExtra("data", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
                startActivity(intent);*/
                Intent intent = new Intent(LSMainActivity.this, MainActivity.class);

                if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName().equals("Food Wear")) {

                    intent.putExtra("title", "footwear");
                } else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName().equals("Hand Bags")) {
                    intent.putExtra("title", "handbags");
                } else{
                    intent.putExtra("title", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName());
                }
                startActivity(intent);
                // Toast.makeText(LSMainActivity.this, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }


    public class SliderPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Activity activity;
        ArrayList<String> image_arraylist;

        public SliderPagerAdapter(Activity activity, ArrayList<String> image_arraylist) {
            this.activity = activity;
            this.image_arraylist = image_arraylist;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
            ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);

            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher);

            Glide.with(activity.getApplicationContext())
                    .load(image_arraylist.get(position))
                    .apply(options)
                    .into(im_slider);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return image_arraylist.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_feed_backs) {
            Intent intent = new Intent(LSMainActivity.this, LSFeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "Hi Friends , \n" +
                    "I came across LadySpyder App in Play Store , Looks attractive with wide range of collections at reasonable rate with free worldwide shipping . For More Info https://ladyspder.com");
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
        } else if (id == R.id.nav_shipment_delivery) {
            Intent intent = new Intent(LSMainActivity.this, LSShippingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_update_password) {
            Intent intent = new Intent(LSMainActivity.this, LSUpdatePasswordActivity.class);
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

    /**
     * Get main categories
     */
    private void getMainCategories() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        APIInterface appInterface = ApiClient.getClient2().create(APIInterface.class);
        Call<GetMainCategoryResponse> call = appInterface.getMainCategories2();
        call.enqueue(new Callback<GetMainCategoryResponse>() {
            @Override
            public void onResponse(Call<GetMainCategoryResponse> call, Response<GetMainCategoryResponse> response) {
                pb.setVisibility(View.GONE);

                try {
                    if (response.body().getError()) {
                        LSApp.getInstance().showToast("Server Error", 0);
                    } else {
                        exListView.setVisibility(View.VISIBLE);
                        try {
                            Gson gson = new Gson();
                            listDataHeader = new ArrayList<String>();
                            listDataChild = new HashMap<>();
                            for (int i = 0; i < response.body().getGetcategory().get(0).size(); i++) {
                                String categoryNmae = response.body().getGetcategory().get(0).get(i).getCategoryName();
                                listDataHeader.add(categoryNmae);
                                List<GetMainCategoryResponse.SubCategory> subCategories = new ArrayList<GetMainCategoryResponse.SubCategory>();
                                subCategories = response.body().getGetcategory().get(0).get(i).getSubCategory();
                                listDataChild.put(categoryNmae, subCategories);
                            }
                            listAdapter = new KDCategoryListAdapter(LSMainActivity.this, listDataHeader, listDataChild);
                            exListView.setAdapter(listAdapter);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GetMainCategoryResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }


    /**
     * Get banners
     */
    private void getBanners() {
/*        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");*/
        APIInterface appInterface = ApiClient.getClient3().create(APIInterface.class);
        Call<LSBannerModel> call = appInterface.getBanners();
        call.enqueue(new Callback<LSBannerModel>() {
            @Override
            public void onResponse(Call<LSBannerModel> call, Response<LSBannerModel> response) {
                hideProgressDialog();
                try {
                    init(response.body().getBanners());
                    addBottomDots(currentpage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LSBannerModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
