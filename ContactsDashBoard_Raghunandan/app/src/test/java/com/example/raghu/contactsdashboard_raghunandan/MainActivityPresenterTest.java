package com.example.raghu.contactsdashboard_raghunandan;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by raghu on 17/5/17.
 */

public class MainActivityPresenterTest {

    public CallLogModel model = CallLogModel.getInstance();

    private CompositeDisposable composite = new CompositeDisposable();

    private MainActivityContract.View view;

    private Single<List<Contacts>> cacher;

    private static final String TAG = "Model";
}
