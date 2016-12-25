package com.example.raghunandan.rxjavatest;

/**
 * Created by Raghunandan on 25-12-2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class TimerExample extends AppCompatActivity {

    private static final String TAG = TimerExample.class.getSimpleName();
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        btn = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doSomething();
            }
        });
    }

    private void doSomething() {

        Observable.interval(1, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .map(new Function<Long, Long>() {

                    @Override
                    public Long apply(Long t) throws Exception {


                        Long value = 10 - t;

                        return value;
                    }

                })
                .takeUntil(new Predicate<Long>() {

                    public boolean test(Long v) {


                        return v == 0;
                    }

                })
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {

            @Override
            public void onSubscribe(Disposable dspsbl) {

            }

            @Override
            public void onNext(Long value) {
                textView.append("Timer value : " + value);
                Log.d(TAG, " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable thrwbl) {

                thrwbl.printStackTrace();
            }

            @Override
            public void onComplete() {

                textView.append("Timer Complete");
            }

        });
    }




}