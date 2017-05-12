package com.example.raghu.contactsdashboard_raghunandan;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by raghu on 12/5/17.
 */

public class MainActivityPresenter implements MainActivityContract.UserActionsListener {


    private  Model model = Model.getInstance();;
    private CompositeDisposable composite = new CompositeDisposable();

    private MainActivityContract.View view;

    private static final String TAG = "Model";
    public MainActivityPresenter(@NonNull MainActivityContract.View view)
    {
        this.view = view;
    }

    @Override
    public void getDetails()
    {

        view.showProgressBar(true);
        DisposableSingleObserver<List<Contacts>> disposableSingleObserver = model.getDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Contacts>>() {
                    @Override
                    public void onSuccess(@NonNull List<Contacts> contacts) {

                      view.showProgressBar(false);
                      view.addItemAdapter(contacts);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        e.printStackTrace();
                    }
                });

        composite.add(disposableSingleObserver);
    }

    @Override
    public void onDestroy() {
        composite.dispose();
    }


}
