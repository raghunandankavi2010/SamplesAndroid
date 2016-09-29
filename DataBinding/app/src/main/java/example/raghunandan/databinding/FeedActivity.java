package example.raghunandan.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

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

        feedViewModel.fetchFeed();
    }

    @Override
    public void onDataChanged(List<FeedModel> model) {


        Log.d("Data"," "+model);
        feedAdapter.addList(model);
    }

    @Override
    public void onProgessBarVisibility(int value) {

        binding.progressBar.setVisibility(value);
    }

    @Override
    public void onRecyclerViewVisibility(int value) {


        Log.d("Visible value "," "+value);
        binding.recyclerview.setVisibility(value);
    }

    @Override
    public void onErrorTextVisibility(int value) {

        binding.errorTextView.setVisibility(value);
    }

    /*public void fetchFeed()
    {
          compositeDisposable.add(dataManager.fetchFeed().subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeWith(new DisposableObserver<FeedResponse>() {

                      @Override
                      public void onError(Throwable e) {
                          e.printStackTrace();
                          binding.progressBar.setVisibility(View.GONE);
                          binding.recyclerview.setVisibility(View.GONE);
                          binding.errorTextView.setVisibility(View.VISIBLE);
                          //EventBus.getDefault().post(new FeedErrorEvent(e));
                      }

                      @Override
                      public void onComplete() {

                      }

                      @Override
                      public void onNext(FeedResponse feedResponse) {
                          binding.progressBar.setVisibility(View.GONE);
                          binding.recyclerview.setVisibility(View.VISIBLE);
                          feedAdapter.addList(feedResponse.getData());
                      }
                  }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }*/
}
