package example.raghunandan.databinding.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import example.raghunandan.databinding.apis.DataManager;
import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.models.FeedResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Raghunandan on 27-09-2016.
 */

public class FeedViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private DataManager dataManager;

    private DataListener datalistener;
    private Context mContext;
    public static final String TAG = "FeedViewModel" ;

    public FeedViewModel(Context context, DataListener datalistener) {

        this.datalistener = datalistener;
        mContext =  context;
        dataManager = new DataManager();
        datalistener.onErrorTextVisibility(View.GONE);
        datalistener.onProgessBarVisibility(View.GONE);
        datalistener.onRecyclerViewVisibility(View.GONE);
    }

    public void fetchFeed() {

        datalistener.onProgessBarVisibility(View.VISIBLE);
        compositeDisposable.add(dataManager.fetchFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FeedResponse>() {



                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        //.set(View.GONE);
                        datalistener.onRecyclerViewVisibility(View.GONE);
                        datalistener.onProgessBarVisibility(View.GONE);
                        datalistener.onErrorTextVisibility(View.VISIBLE);
                        //EventBus.getDefault().post(new FeedErrorEvent(e));
                    }

                    @Override
                    public void onComplete() {


                    }

                    @Override
                    public void onNext(FeedResponse feedResponse) {

                        Log.d(TAG,"OnNext called");
                        if(datalistener!=null) {
                            datalistener.onRecyclerViewVisibility(View.VISIBLE);
                            datalistener.onProgessBarVisibility(View.GONE);
                            datalistener.onErrorTextVisibility(View.GONE);
                            datalistener.onDataChanged(feedResponse.getData());
                        }


                    }
                }));
    }

    public interface DataListener
    {
        void onDataChanged(List<FeedModel> model);
        void onProgessBarVisibility(int value);
        void onRecyclerViewVisibility(int value);
        void onErrorTextVisibility(int value);
    }
}
