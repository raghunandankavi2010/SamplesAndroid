package com.peakaeriest.ladyspyder.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.adapters.KDSubCategoryAdapter;
import com.peakaeriest.ladyspyder.models.KDProductsPojo;

import java.util.ArrayList;

public class LSWishListActivity extends LSBaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsfavorite);

        updateFragmentTitle("WishList", true, false);
        recyclerView = (RecyclerView) findViewById(R.id.rvChallenge);
        getLocalArray();
        recyclerView.setLayoutManager(new LinearLayoutManager(LSWishListActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new LSWishListActivity.RecyclerTouchListener(LSWishListActivity.this, recyclerView, new LSWishListActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
              /*  Intent intent = new Intent(LSWishListActivity.this, LSProductDetailActvity.class);
                startActivity(intent);*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setNestedScrollingEnabled(false);
    }

    private void getLocalArray() {

        final ArrayList<KDProductsPojo> mExamples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            KDProductsPojo example = new KDProductsPojo();
            example.setProductName("Medjool Dates Large Sizet" + i);
            example.setHalfKg("125 KG" + i);
            example.setCarMake("KA05M2837" + i);
            example.setCarimage(i);
            example.setPriceDetails("OMR 0.12 " + i);
            mExamples.add(example);
        }

        KDSubCategoryAdapter mAdapter = new KDSubCategoryAdapter(LSWishListActivity.this, mExamples);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kd_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private LSWishListActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final LSWishListActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(LSWishListActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


        }
    }
}
