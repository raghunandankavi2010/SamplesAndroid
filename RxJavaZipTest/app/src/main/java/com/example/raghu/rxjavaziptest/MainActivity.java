package com.example.raghu.rxjavaziptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.learn2crack.com/";

    private RecyclerView mRecyclerView;

    private Disposable disposable;

    private static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON() {

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);


        Observable<List<Android>> listObservable = requestInterface.register();

        Observable<List<Android>> listObservable2 = requestInterface.register();


        disposable  = Observable.zip(listObservable, listObservable2,

                new BiFunction<List<Android>, List<Android>, CombinedAndroid>() {
                    @Override
                    public CombinedAndroid apply(List<Android> list1, List<Android> list2) throws Exception {
                        return new CombinedAndroid().addAll(list1,list2);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<CombinedAndroid>() {

                    @Override
                    public void onNext(CombinedAndroid combinedAndroid) {

                        Log.i(TAG,"Name "+combinedAndroid.getFinalList().size());
                     for(int i=0;i<combinedAndroid.getFinalList().size();i++)
                     {
                         Log.i(TAG,"Name "+combinedAndroid.getFinalList().get(i).getName());
                     }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}