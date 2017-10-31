package com.example.raghu.drawersample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private SimpleItemRecyclerViewAdapter mAdapter;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private int draawables[] = {R.drawable.ic_action_add,
            R.drawable.ic_action_bookmark,
            R.drawable.ic_action_explore,
            R.drawable.ic_action_favorite,
            R.drawable.ic_action_help};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpNavDrawer();

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new SimpleItemRecyclerViewAdapter(this, getItems(),this);
        recyclerView.setAdapter(mAdapter);

    }


    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }


    }


    @Override
    public void onItemClick(int pos) {
        mAdapter.setSelectedPosition(pos);
        mAdapter.notifyDataSetChanged();
    }

    public List<Items>  getItems() {
        List<Items> mList = new ArrayList<>(10);

        Items header = new Items();
        header.setHeader(0);
        mList.add(header);
        Items space = new Items();
        space.setSpace(1);
        mList.add(space);
        for(int i=0;i<10;i++){
            Items items = new Items();
            items.setName("Item "+i);
            items.setDrawable(draawables[0]);
            mList.add(items);
        }
        Items section5 = new Items();
        section5.setSection(true,"Section");
        mList.add(5,section5);

       /* Items divitems5 = new Items();
        divitems5.setDivider(true);
        mList.add(5,divitems5);*/

        Items divitems3 = new Items();
        divitems3.setDivider(true);
        mList.add(3,divitems3);
        return mList;
    }
}
