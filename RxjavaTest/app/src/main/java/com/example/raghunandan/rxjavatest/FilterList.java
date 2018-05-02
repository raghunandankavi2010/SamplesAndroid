package com.example.raghunandan.rxjavatest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class FilterList extends AppCompatActivity {

    /**
     * Sample that shows filtering of list
     * with filter and Predicate
     *
     */

    private TextView tv;
    private Disposable disposable;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_filter);
        tv = findViewById(R.id.textView2);
        Observable<List<Integer>>  listObservable = Observable.just(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        disposable = listObservable.flatMapIterable(new Function<List<Integer>, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> apply(List<Integer> integers) {
                return integers;
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer v) {
                return v % 2 == 0;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                tv.append(String.valueOf(integer));
                tv.append("\n");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!disposable.isDisposed())
            disposable.dispose();

    }
}
