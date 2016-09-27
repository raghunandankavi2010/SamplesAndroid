package example.raghunandan.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import example.raghunandan.databinding.adapter.OSAdapter;
import example.raghunandan.databinding.databinding.ListScreenBinding;
import example.raghunandan.databinding.models.OperatingSystems;

/**
 * Created by Raghunandan on 24-09-2016.
 */

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListScreenBinding binding =
                DataBindingUtil.setContentView(this, R.layout.list_screen);
        binding.toolbar.setTitle("DataBinding-Recyclerview");

        binding.recycler.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(mLayoutManager);

        List<OperatingSystems> mList = new ArrayList<>();

        mList.add(new OperatingSystems("Android"));
        mList.add(new OperatingSystems("Windows"));
        mList.add(new OperatingSystems("RedHat"));
        mList.add(new OperatingSystems("Ubuntu"));
        mList.add(new OperatingSystems("Mandriva"));
        mList.add(new OperatingSystems("Mac"));

        OSAdapter osAdapter = new OSAdapter(mList);
        binding.recycler.setAdapter(osAdapter);
    }
}
