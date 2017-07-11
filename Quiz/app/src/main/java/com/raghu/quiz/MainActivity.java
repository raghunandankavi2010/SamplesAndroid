package com.raghu.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.raghu.quiz.helper.OnListChangedListener;
import com.raghu.quiz.helper.OnStartDragListener;
import com.raghu.quiz.helper.SimpleItemTouchHelperCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnStartDragListener, OnListChangedListener {

    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private List<String> listChanged = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String json = loadJSONFromAsset();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Questions");
            for(int i=0;i<jsonArray.length();i++) {
                String temp = (String) jsonArray.get(i);
                String[] temparr =  temp.split("\\$");
                stringList = Arrays.asList(temparr);

            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        adapter = new RecyclerListAdapter(this, this,this);
        adapter.setOriginal(stringList );
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                check();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public void check()
    {

        if(adapter!=null) {


            if (adapter.getList().equals(getListChanged()))
            {
                Toast.makeText(this,"Answer Correct",Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this,"Answer Not Correct",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void setListChanged(List<String> listChanged) {
        this.listChanged = listChanged;
    }

    public List<String> getListChanged() {
        return listChanged;
    }

    @Override
    public void onNoteListChanged(List<String> listChanged) {

        setListChanged(listChanged);
    }
}



