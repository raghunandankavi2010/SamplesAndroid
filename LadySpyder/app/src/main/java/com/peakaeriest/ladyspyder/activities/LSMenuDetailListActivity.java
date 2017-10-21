package com.peakaeriest.ladyspyder.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.adapters.LSHomeDetailAdapter;
import com.peakaeriest.ladyspyder.models.LSMenuPojo;

import java.util.ArrayList;

public class LSMenuDetailListActivity extends LSBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsmenu_detail_list);


        ArrayList<LSMenuPojo> mHomeList = new ArrayList<>();
        updateFragmentTitle("Subcategory", true, false);

        RecyclerView rvMenu = (RecyclerView) findViewById(R.id.vertical_recycler_view);

        LSMenuPojo lsMenuPojo = new LSMenuPojo();
        lsMenuPojo.setmPhoto(R.drawable.ic_food_wear);
        lsMenuPojo.setmTitle("Footwear");
        mHomeList.add(lsMenuPojo);

        LSMenuPojo menuPojo = new LSMenuPojo();
        menuPojo.setmPhoto(R.drawable.ic_bags);
        menuPojo.setmTitle("Handbags");
        mHomeList.add(menuPojo);

        LSMenuPojo pojo = new LSMenuPojo();
        pojo.setmPhoto(R.drawable.ic_jewellery);
        pojo.setmTitle("Jewelry");
        mHomeList.add(pojo);

        LSMenuPojo pojo1 = new LSMenuPojo();
        pojo1.setmPhoto(R.drawable.ic_phone_cass);
        pojo1.setmTitle("Phone Cases");
        mHomeList.add(pojo1);

        LSMenuPojo menuPojo1 = new LSMenuPojo();
        menuPojo1.setmPhoto(R.drawable.ic_posters);
        menuPojo1.setmTitle("Posters");
        mHomeList.add(menuPojo1);

        LSMenuPojo menuPojo2 = new LSMenuPojo();
        menuPojo2.setmPhoto(R.drawable.ic_women_clothing);
        menuPojo2.setmTitle("Women Clothing");
        mHomeList.add(menuPojo2);

        LSHomeDetailAdapter mAdapter = new LSHomeDetailAdapter(LSMenuDetailListActivity.this, mHomeList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(LSMenuDetailListActivity.this, LinearLayoutManager.VERTICAL, false);
        rvMenu.setLayoutManager(horizontalLayoutManagaer);
        rvMenu.setAdapter(mAdapter);
        rvMenu.setNestedScrollingEnabled(false);
    }
}
