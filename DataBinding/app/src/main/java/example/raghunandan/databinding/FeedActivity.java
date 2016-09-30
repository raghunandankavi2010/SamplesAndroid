package example.raghunandan.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import example.raghunandan.databinding.adapter.FeedAdapter;
import example.raghunandan.databinding.databinding.FeedActivityBinding;
import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.viewmodel.FeedViewModel;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Raghunandan on 25-09-2016.
 */

public class FeedActivity extends AppCompatActivity implements FeedViewModel.DataListener {


    private FeedAdapter feedAdapter;

    private FeedActivityBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.feed_activity);

        setSupportActionBar(binding.toolbar);

        FeedViewModel feedViewModel = new FeedViewModel(this,this);

        binding.recyclerview.setLayoutManager( new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(true);

        feedAdapter = new FeedAdapter();
        binding.recyclerview.setAdapter(feedAdapter);

        binding.recyclerview.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorTextView.setVisibility(View.GONE);

        feedViewModel.fetchFeed();
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataChanged(List<FeedModel> model) {


        binding.recyclerview.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorTextView.setVisibility(View.GONE);
        Log.d("Data"," "+model);
        feedAdapter.addList(model);
    }

    @Override
    public void onError() {

        binding.recyclerview.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
        binding.errorTextView.setVisibility(View.VISIBLE);

    }



}
