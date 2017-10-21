package com.peakaeriest.ladyspyder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.adapters.MyOrderAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LSMyOrdersActivity extends LSBaseActivity {
    MyOrderAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsmy_orders);
        updateFragmentTitle("My Orders", true, false);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        updateFragmentTitle("My Orders", true, false);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new MyOrderAdapter(LSMyOrdersActivity.this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LSMyOrdersActivity.this, LSOrderDetailActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    /*
 * Preparing the list data
 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Fri,Sep 1 2017 8:15 pm");
        listDataHeader.add("Sun,Aug 30 2017 10:15 pm");
        listDataHeader.add("Sat,Aug 18 2017 8:15 pm");
        listDataHeader.add("Fri,Aug 4 2017 2:15 pm");
        listDataHeader.add("Mon,Aug 2 2017 1:15 pm");
        listDataHeader.add("Tue,Aug 1 2017 4:15 pm");

        // Adding child data
        List<String> fruitsvegtablesList = new ArrayList<String>();
        fruitsvegtablesList.add("All fruits & Vegetables");
        fruitsvegtablesList.add("Fresh Vegetables");
        fruitsvegtablesList.add("Fresh Herbs & Seasonings");
        fruitsvegtablesList.add("Fresh Fruits");
        fruitsvegtablesList.add("Cuts & Sprouts");
        fruitsvegtablesList.add("Organic Fruits & Vegetables");
        fruitsvegtablesList.add("Flowers");

        List<String> organicList = new ArrayList<String>();
        organicList.add("Organic Fruits & Vegetables");

        List<String> grocerisList = new ArrayList<String>();
        grocerisList.add("Rice & Rice Products");
        grocerisList.add("Flours & Sooji");
        grocerisList.add("Dals & Pulses");
        grocerisList.add("Salt Sugar & Jaggery");
        grocerisList.add("Edible oil & Ghee");
        grocerisList.add("Dry fruits");

        List<String> beverageList = new ArrayList<String>();
        beverageList.add("All Beverages");
        beverageList.add("Mineral Water");
        beverageList.add("Tea & Cofee");
        beverageList.add("Energy & Heath drinks");
        beverageList.add("Edible oil & Ghee");
        beverageList.add("Dry fruits");

        List<String> fashionList = new ArrayList<String>();
        fashionList.add("All Beverages");

        List<String> electronicList = new ArrayList<String>();
        electronicList.add("All Beverages");
        electronicList.add("Mineral Water");
        electronicList.add("Tea & Cofee");
        electronicList.add("Energy & Heath drinks");
        electronicList.add("Edible oil & Ghee");
        electronicList.add("Dry fruits");

        listDataChild.put(listDataHeader.get(0), fruitsvegtablesList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), organicList);
        listDataChild.put(listDataHeader.get(2), grocerisList);
        listDataChild.put(listDataHeader.get(3), electronicList);
        listDataChild.put(listDataHeader.get(4), fashionList);
        listDataChild.put(listDataHeader.get(5), electronicList);


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
}
