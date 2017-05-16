package com.example.raghu.contactsdashboard_raghunandan;

import android.util.Log;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by raghu on 12/5/17.
 */

public class MainActivityPresenter implements MainActivityContract.UserActionsListener {


    //public Model model = Model.getInstance();
    public CallLogModel model = CallLogModel.getInstance();

    private CompositeDisposable composite = new CompositeDisposable();

    private MainActivityContract.View view;

    private Single<List<Contacts>> cacher;

    private static final String TAG = "Model";



    public MainActivityPresenter(@NonNull MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void getDetails(boolean resetCache) {


       view.showProgressBar(true);
        cacher = model.getDetails(resetCache);


        cacher.doOnSuccess(new Consumer<List<Contacts>>() {
            @Override
            public void accept(@NonNull List<Contacts> contactses) throws Exception {
                Log.i(TAG,"completed");
            }
        });
        Disposable disposable = cacher.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contacts>>() {
                    @Override
                    public void accept(@NonNull List<Contacts> contactses) throws Exception {
                        view.showProgressBar(false);
                        view.addItemAdapter(contactses);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        composite.add(disposable);
    }

    @Override
    public void onDestroy() {
        composite.dispose();

    }



}
