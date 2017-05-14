package com.example.raghu.rxjava2invalidatecache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) this.findViewById(R.id.textView);
        test();
    }

    public void test() {
        Single<Integer> o = Single.just(1)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                        textView.append("Completed \n");
                        Log.i(TAG, "completed");
                    }

                });
        OnSubScribeRefreshingCache<Integer> cacher =
                new OnSubScribeRefreshingCache<Integer>(o);
        Single<Integer> o2 = Single.unsafeCreate(cacher);
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        cacher.reset();
        textView.append("Cache Reset" +"\n");
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
    }
}
