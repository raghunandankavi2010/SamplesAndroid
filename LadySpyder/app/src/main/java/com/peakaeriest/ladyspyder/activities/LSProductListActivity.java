package com.peakaeriest.ladyspyder.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.peakaeriest.ladyspyder.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.peakaeriest.ladyspyder.adapters.LSProductListAdapter;
import com.peakaeriest.ladyspyder.models.LSProductPojo;

import java.util.ArrayList;

public class LSProductListActivity extends LSBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsproduct_list);
        updateFragmentTitle("Footwear", true, false);
        ArrayList<LSProductPojo> mHomeList = new ArrayList<>();
        RecyclerView mMenuAdapter = (RecyclerView) findViewById(R.id.vertical_recycler_view);

        for (int i = 0; i < 100; i++) {
            LSProductPojo lsMenuPojo = new LSProductPojo();
            lsMenuPojo.setProductName("Footwear");
            mHomeList.add(lsMenuPojo);
        }


        LSProductListAdapter mAdapter = new LSProductListAdapter(LSProductListActivity.this, mHomeList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(LSProductListActivity.this, LinearLayoutManager.VERTICAL, false);
        mMenuAdapter.setLayoutManager(horizontalLayoutManagaer);
        mMenuAdapter.setAdapter(mAdapter);
        mMenuAdapter.setNestedScrollingEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        int badgeCount = 12;
        ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge),
                FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, badgeCount);
        ActionItemBadge.update(this, menu.findItem(R.id.it_fav),
                FontAwesome.Icon.faw_heart, ActionItemBadge.BadgeStyles.RED, 5);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_samplebadge:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
