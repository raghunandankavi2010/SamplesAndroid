package example.raghunandan.databinding.viewmodel;

import android.content.Context;
import android.database.Observable;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import java.util.List;

import example.raghunandan.databinding.apis.DataManager;
import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.models.FeedResponse;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Raghunandan on 27-09-2016.
 */

public class FeedViewModel {

    public DataManager dataManager;

    private DataListener datalistener;
    private Context mContext;

    public FeedViewModel(Context context, DataListener datalistener,DataManager dataManager) {

        this.datalistener = datalistener;
        mContext =  context;
        this.dataManager = dataManager;
    }



    public void fetchFeed() {

          io.reactivex.Observable<FeedResponse> m = dataManager.fetchFeed();
               m.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FeedResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        datalistener.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                    @Override
                    public void onNext(FeedResponse feedResponse) {
                        if(datalistener!=null) {
                            datalistener.onDataChanged(feedResponse.getData());
                        }
                    }
                });
    }


    public interface DataListener
    {
        void onDataChanged(List<FeedModel> model);
        void onError();

    }
}
