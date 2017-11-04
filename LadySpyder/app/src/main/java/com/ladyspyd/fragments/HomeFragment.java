package com.ladyspyd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.gson.JsonSyntaxException;

import com.ladyspyd.R;
import com.ladyspyd.activities.MainActivity;
import com.ladyspyd.adapters.LSCategoryListAdapter;
import com.ladyspyd.helpers.LSApp;
import com.ladyspyd.helpers.LSCheckNetwork;
import com.ladyspyd.models.GetMainCategoryResponse;
import com.ladyspyd.models.LSBannerModel;
import com.ladyspyd.models.LSPopularProductPojo;
import com.ladyspyd.rest.APIInterface;
import com.ladyspyd.rest.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private List<LSPopularProductPojo> mProductPojo;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    private SliderPagerAdapter sliderPagerAdapter;
    private ArrayList<String> slider_image_list;
    private TextView[] dots;
    private int currentpage = 0;
    private LSCategoryListAdapter listAdapter;
    private ExpandableListView exListView;
    private  List<String> listDataHeader;
    private HashMap<String, List<GetMainCategoryResponse.SubCategory>> listDataChild;
    private ProgressBar pb;
    private Handler handler = new Handler();
    private View view;
    private OnHeadlineSelectedListener mCallback;

    public HomeFragment() {
        // Required empty public constructor
    }

    public interface OnHeadlineSelectedListener {
        /**
         * Called by HomeFragment when a list item is selected
         */
        public void onCategoriesSelected(String position);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LSCheckNetwork.isInternetAvailable(getActivity())) {
            getMainCategories();
            getBanners();
        } else {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.kd_home_fragment, container, false);

        mProductPojo = new ArrayList<>();
        exListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        exListView.setVisibility(View.GONE);
        pb = (ProgressBar) view.findViewById(R.id.pb);



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
                if (listDataHeader.get(groupPosition) != null && listDataChild.get(listDataHeader.get(groupPosition)) == null) {
                    String title = listDataHeader.get(groupPosition);
                    title = title.replace(' ', '-');

                    //Toast.makeText(LSMainActivity.this, listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("title", title);
                    try {
                        JSONObject props = new JSONObject();
                        props.put("Category Selected", title);
                        // getMixPanel(LSMainActivity.this).track("Category Selection Activity - Category Success called", props);
                    } catch (JSONException e) {
                        Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                    }
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
                Intent intent = new Intent(getActivity(), MainActivity.class);

         /*       if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName().equals("Food Wear")) {
                    mCallback.onCategoriesSelected("footwear");
                    LSApp.getInstance().getPrefs().setTitle("footwear");
                    intent.putExtra("title", "footwear");
                } else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName().equals("Hand Bags")) {
                    mCallback.onCategoriesSelected("handbags");
                    LSApp.getInstance().getPrefs().setTitle("handbags");

                    intent.putExtra("title", "handbags");
                } else {*/
                    mCallback.onCategoriesSelected(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName());
                    LSApp.getInstance().getPrefs().setTitle(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName());
                    intent.putExtra("title", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName());
                //}

                // startActivity(intent);
                // Toast.makeText(LSMainActivity.this, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSubCategoryName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    private void init(List<LSBannerModel.Banner> banners) {
        try {
            vp_slider = (ViewPager) view.findViewById(R.id.vp_slider);
            ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
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
            sliderPagerAdapter = new SliderPagerAdapter(getActivity(), slider_image_list);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
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
            try {
                ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher);
                Glide.with(getActivity())
                        .load(image_arraylist.get(position))
                        .apply(options)
                        .into(im_slider);
                container.addView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                            listDataHeader = new ArrayList<String>();
                            listDataChild = new HashMap<>();
                            for (int i = 0; i < response.body().getGetcategory().get(0).size(); i++) {
                                String categoryNmae = response.body().getGetcategory().get(0).get(i).getCategoryName();
                                listDataHeader.add(categoryNmae);
                                List<GetMainCategoryResponse.SubCategory> subCategories = new ArrayList<GetMainCategoryResponse.SubCategory>();
                                subCategories = response.body().getGetcategory().get(0).get(i).getSubCategory();
                                listDataChild.put(categoryNmae, subCategories);
                            }
                            listAdapter = new LSCategoryListAdapter(getActivity(), listDataHeader, listDataChild);
                            exListView.setAdapter(listAdapter);
                         try {

                                if (getResources().getBoolean(R.bool.isTablet)) {
                                    LSApp.getInstance().getPrefs().setTitle("women-clothing");
                                    mCallback.onCategoriesSelected("women-clothing");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

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


    public boolean getIsTablet() {
        boolean isTablet = false;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            // 6.5inch device or bigger
            isTablet = true;
        } else {
            // smaller device
            isTablet = false;
        }
        return isTablet;
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
                //hideProgressDialog();
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
