package com.peakaeriest.ladyspyder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.activities.LSBaseActivity;

import java.util.ArrayList;

public class LSProductDetailActivity extends LSBaseActivity {
    int currentpage = 0;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdproduct_detail);

        updateFragmentTitle("Product Detail", true, false);

        init();
        addBottomDots(currentpage);

    }

    private void init() {
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        slider_image_list = new ArrayList<>();
        slider_image_list.add("https://images-eu.ssl-images-amazon.com/images/G/31//img16/men-apparel/stores/shirt/casual._V507777836_.jpg");
        slider_image_list.add("https://images-eu.ssl-images-amazon.com/images/G/31//img16/men-apparel/stores/shirt/pastel._V507777836_.jpg");
        slider_image_list.add("http://images.halloweencostumes.net/products/5038/1-1/white-renaissance-shirt.jpg");
        slider_image_list.add("https://johnlewis.scene7.com/is/image/JohnLewis/003198981?$prod_main$");
        slider_image_list.add("http://www.thinkgeek.com/images/products/frontsquare/jnpi_punisher_short_shirt_mb.jpg");


        sliderPagerAdapter = new SliderPagerAdapter(LSProductDetailActivity.this, slider_image_list);
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
        Context activity;
        ArrayList<String> image_arraylist;

        public SliderPagerAdapter(Context activity, ArrayList<String> image_arraylist) {
            this.activity = activity;
            this.image_arraylist = image_arraylist;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
            ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);

            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
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
}
